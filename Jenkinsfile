@NonCPS
def modules() {
	def  directories = [
		"sysgine-framework-test",
		"sysgine-framework-common",
		"sysgine-framework-reflection"
	]
	directories
}
def mavenProjects = "--projects sysgine-framework-test,sysgine-framework-common,sysgine-framework-reflection"

pipeline {
	agent any

	tools {
		maven "maven"
	}

    options {
        timeout(time: 15, unit: 'MINUTES')
    }

	stages {
        stage('Initialization') {
            steps {
                echo "Building Branch: ${env.BRANCH_NAME}"
                echo "Using PATH = ${env.PATH}"
				echo "M2_HOME = ${M2_HOME}"
				echo "JAVA_HOME = ${JAVA_HOME}"
				sh "java -version"
				sh "javac -version"
            }
        }

		stage('Checkout') {
			steps {
				git branch: "${env.BRANCH_NAME}", url: "${env.GIT_URL}"
			}
		}

		stage('Build') {
			steps {
				script {
					sh "mvn clean validate compile package verify install -Dmaven.test.skip=true -e ${mavenProjects}"
				}
			}
		}

		stage('JUnit') {
			steps {
				script {
					sh "mvn test -Dmaven.test.redirectTestOutputToFile=true -e ${mavenProjects}"
				}
			}
			/*
			post {
				always {
					script {
						for (module in modules()) {
                   			junit(testResults: "${module}/target/surefire-reports/*.xml", allowEmptyResults: true)
						}
					}
				}
			}
			*/
		}

		stage('SonarQube Scan & Quality Gate') {
			steps {
				script {
					for (module in junitModules()) {
						dir("${env.WORKSPACE}/${module}") {
							withSonarQubeEnv('sonarqube-server') {
								sh "mvn sonar:sonar -e"
							}
						}

                        script {
                            timeout(time: 5, unit: 'MINUTES') {
                                def response = waitForQualityGate()
                                if (response.status != 'OK') {
                                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                                }
                            }
                        }
					}
				}
			}
		}

		stage("Publish to Nexus") {
			steps {
				script {
					for (module in modules()) {
						dir("${env.WORKSPACE}/${module}") {
							script {
								pom = readMavenPom file: "pom.xml";
								filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
								echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
								artifactPath = filesByGlob[0].path;
								artifactExists = fileExists artifactPath;
								if(artifactExists) {
									nexusPublisher nexusInstanceId: 'nexus3-repository-server', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "target/${pom.artifactId}-${pom.version}.${pom.packaging}"]], mavenCoordinate: [artifactId: pom.artifactId, groupId: pom.parent.groupId, packaging: pom.packaging, version: pom.version]]]
								} else {
									error "*** File: ${artifactPath}, could not be found";
								}
							}
						}
					}
				}
			}
		}
	}
}
