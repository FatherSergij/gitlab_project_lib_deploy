pipeline {
    agent any
    environment {
        IP_K8S="16.170.42.2"
        AWS_ACCOUNT_ID="728490037630"
        AWS_REGION="eu-north-1"
        BRANCH="${params.Branch}"
        TAG="${params.ImageTag}" 
        SERVICE="${params.Service}"       
        BRANCH_DEV="${params.Branch_dev}"
        TAG_DEV="${params.ImageTag_dev}" 
        SERVICE_DEV="${params.Service_dev}"        
        //BRANCH2="develop" 
    }    
    
    //parameters {
    //    string defaultValue: '', description: 'K', name: 'BRANCHBUILD'
    //   // string defaultValue: '', description: 'G', name: 'TAGBUILD'
    //}    
    
    stages {       

        stage('Logging into AWS ECR') {
            steps {
                script {
                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
                }
            }
        }


        stage('Deploy on k8s from nginx-phpfpm') {

            steps {
          //sh scp -o StrictHostKeyChecking=no -r repos/project_lib_deploy/ ubuntu@${IP_K8S}:~/"
                script {
                    echo "${params.Branch_dev}"
                    echo "${params.ImageTag_dev}"
                    echo "${params.Service_dev}"
                    echo "${BRANCH_DEV}"
                    echo "${TAG_DEV}"
                    echo "${SERVICE_DEV}"
                    if (params.Branch_dev == 'develop') {
                        sh 'ssh ubuntu@${IP_K8S} 
                        """cd repos/project_lib_deploy; 
                        echo "${BRANCH_DEV}"; 
                        echo "${TAG_DEV}"; 
                        echo "${SERVICE_DEV}"; 
                        export BRANCH=${BRANCH_DEV}; 
                        echo ${env.BRANCH_DEV}; 
                        export TAG=${TAG_DEV}; 
                        export SERVICE=${SERVICE_DEV}; 
                        kubectl create namespace ${BRANCH_DEV}; 
                        kubectl apply -f issuer.yaml; 
                        envsubst < ingress.yaml | kubectl apply -f -; 
                        kubectl delete -n ${BRANCH_DEV} secret regcred --ignore-not-found; 
                        kubectl create secret docker-registry regcred 
                                --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com 
                                --docker-username=AWS 
                                --docker-password=$(aws ecr get-login-password --region ${AWS_REGION}) 
                                --namespace=${BRANCH_DEV}; 
                        envsubst < service-nginx-phpfpm.yaml | kubectl apply -f -; 
                        kubectl delete deploy deploy-nginx-phpfpm -n ${BRANCH_DEV}; 
                        envsubst < deploy-nginx-phpfpm.yaml | kubectl apply -f -;"""'                               
                    } else {
                        sh 'ssh ubuntu@${IP_K8S} \
                        """cd repos/project_lib_deploy; \
                        export BRANCH=${BRANCH}; \
                        export TAG=${TAG}; \
                        export SERVICE=${SERVICE}; \
                        kubectl create namespace ${BRANCH}; \
                        kubectl apply -f issuer.yaml; \
                        envsubst < ingress.yaml | kubectl apply -f -; \
                        kubectl delete -n ${BRANCH} secret regcred --ignore-not-found; \
                        kubectl create secret docker-registry regcred \
                                --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com \
                                --docker-username=AWS \
                                --docker-password=$(aws ecr get-login-password --region ${AWS_REGION}) \
                                --namespace=${BRANCH}; \
                        envsubst < service-nginx-phpfpm.yaml | kubectl apply -f -; \
                        kubectl delete deploy deploy-nginx-phpfpm -n ${BRANCH}; \
                        envsubst < deploy-nginx-phpfpm.yaml | kubectl apply -f -;"""' 
                    }
                }                                                    
            }
        } 
    }
}