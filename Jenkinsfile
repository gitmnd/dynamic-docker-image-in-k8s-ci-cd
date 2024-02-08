@Library('github.com/gitmnd/groovy-framework-libs@main') _

import SetEnv

def buildDeploymentVersion = "${Calendar.instance.format('y.DDD')}.${env.BUILD_NUMBER}" as String
def nonprodEnvironment = SetEnv.SET_ENV["nonprod"]
def prodEnvironment = SetEnv.SET_ENV["prod"]
def microServiceName = "cicd-kubernetes-image-version-jenkins"
def dockerImageTag = ""
def awsEcrdockerImageTag = ""

def allVersions(){
    try{
        bat 'java --version'
    }
   finally{

   }
}

pipeline {
  agent any

   tools {
          maven 'maven'
          jdk 'jdk'
   }
  environment{
      PATH = "/usr/local/bin:$PATH"
      BRANCH_NAME = "${env.BRANCH_NAME}"
  }

  stages {

    stage("Branch") {
            steps{
                script {
                    echo "Branch Name: ${BRANCH_NAME}"
                }
            }
        }

    stage('[NON PRODUCTION] Build Docker image') {
                steps {
                    script {
                        def currentBuildDirectory = pwd()
                        echo "Workspace Directory: ${currentBuildDirectory}"
                        dockerImageTag = buildDockerImage(microServiceName, buildDeploymentVersion, currentBuildDirectory)
                    }
                }
     }

      stage('[NON PRODUCTION] Publish docker image to ECR') {
                 steps {
                     script {
                         awsEcrdockerImageTag = createdockerImageTag(microServiceName, buildDeploymentVersion, nonprodEnvironment.accountNumber, nonprodEnvironment.region, nonprodEnvironment.name)
                         pushDokcerImageToEcr(nonprodEnvironment.accountNumber, nonprodEnvironment.region, dockerImageTag, awsEcrdockerImageTag)
                     }
                 }
      }

      stage('[NON PRODUCTION] Deploy to EKS') {
                  steps {
                      script {
                          substituteHelper(
                                  "./deployment.yml",
                                  [
                                           "CONTAINER_IMAGE": "${awsEcrdockerImageTag}",
                                           "ENV" : "prod",
                                           "REPLICA_SET": "1",
                                           "IMAGE_PULL_POLICY": "IfNotPresent",
                                           "NAMESPACE": "default"
                                  ],
                                  "./nonprod_deployment_eks.yml")

                          println "Deploying service to Non-prod EKS"

                          withKubeConfig([credentialsId: 'nonprod', serverUrl: 'https://kubernetes.docker.internal:6443']) {
                              bat """
                                  kubectl apply -f nonprod_deployment_eks.yml
                                  kubectl get pods
                                  kubectl get svc
                              """
                          }

                          println "Deployed image: '${dockerImageTag}' to '${nonprodEnvironment.name}' EKS"
                  }
              }
      }

      stage("[PRODUCTION] Approve") {
                  when {
                      branch "main"
                    }
                   steps {
                      timedApproval(prodEnvironment.name, 5, 'MINUTES')
                    }
      }

      stage(' [PRODUCTION] Publish docker image to ECR') {
                       when {
                             branch "main"
                            }
                       steps {
                           script {
                               awsEcrdockerImageTag = createdockerImageTag(microServiceName, buildDeploymentVersion, prodEnvironment.accountNumber, prodEnvironment.region, prodEnvironment.name)
                               pushDokcerImageToEcr(prodEnvironment.accountNumber, prodEnvironment.region, dockerImageTag, awsEcrdockerImageTag)
                           }
                       }
      }

      stage(' [PRODUCTION] Deploy to EKS') {
                        steps {
                            script {
                                substituteHelper(
                                        "./deployment.yml",
                                        [
                                                "CONTAINER_IMAGE": "${awsEcrdockerImageTag}",
                                                "ENV" : "prod",
                                                "REPLICA_SET": "1",
                                                "IMAGE_PULL_POLICY": "IfNotPresent",
                                                "NAMESPACE": "default"
                                        ],
                                        "./prod_deployment_eks.yml")

                                println "Deploying service to Production EKS"

                                withKubeConfig([credentialsId: 'prod', serverUrl: 'https://kubernetes.docker.internal:6443']) {
                                    bat """
                                        kubectl apply -f prod_deployment_eks.yml
                                        kubectl get pods
                                        kubectl get svc
                                    """
                                }

                                println "Deployed image  : '${dockerImageTag}' to '${prodEnvironment.name}' EKS"
                        }
                    }
      }
  }

   post {
          success {
              echo "Build successful..."
          }
          failure {
              echo "Build failed..."
          }
   }


}