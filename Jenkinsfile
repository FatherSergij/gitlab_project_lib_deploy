@Library('lib-for-project') _
pipeline {
    agent any
    environment {
        // IP_K8S="16.170.42.2"
         AWS_ACCOUNT_ID="728490037630"
         AWS_REGION="eu-north-1"
        BRANCH="${params.BranchRun}"
        TAG="${params.ImageTag}" 
        SERVICE="${params.ServiceRun}"       
        BRANCH_DEV="${params.BranchRun_dev}"
        TAG_DEV="${params.ImageTag_dev}" 
        SERVICE_DEV="${params.ServiceRun_dev}"        
    }    
    
    // libraries {
    //      lib('lib-for-project')
    // }    
    
    stages {       

        stage('Logging into AWS ECR') {
            steps {
                script {
                    LogToEcr()
                    //sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
                }
            }
        }


        stage('Deploy on k8s from nginx-phpfpm') {

            steps {
          //sh scp -o StrictHostKeyChecking=no -r repos/project_lib_deploy/ ubuntu@${IP_K8S}:~/"
                script {
                    if ("${BRANCH_DEV}" == 'develop') {
                        Deploy "${BRANCH_DEV}", "${TAG_DEV}", "${SERVICE_DEV}"
                    //     sh 'ssh ubuntu@${IP_K8S} \
                    //     """cd repos/project_lib_deploy/yaml; \
                    //     export BRANCH=${BRANCH_DEV}; \
                    //     export TAG=${TAG_DEV}; \
                    //     export SERVICE=${SERVICE_DEV}; \
                    //     kubectl create namespace ${BRANCH_DEV}; \
                    //     kubectl apply -f issuer.yaml; \
                    //     envsubst < ingress.yaml | kubectl apply -f -; \
                    //     kubectl delete -n ${BRANCH_DEV} secret regcred --ignore-not-found; \
                    //     kubectl create secret docker-registry regcred \
                    //             --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com \
                    //             --docker-username=AWS \
                    //             --docker-password=$(aws ecr get-login-password --region ${AWS_REGION}) \
                    //             --namespace=${BRANCH_DEV}; \
                    //     envsubst < service-${SERVICE_DEV}.yaml | kubectl apply -f -; \
                    //     kubectl delete deploy deploy-${SERVICE_DEV} -n ${BRANCH_DEV}; \
                    //     envsubst < deploy-${SERVICE_DEV}.yaml | kubectl apply -f -;"""'
                    } else {
                    //    Deploy// "${BRANCH}", "${TAG}", "${SERVICE}"
                    //     sh 'ssh ubuntu@${IP_K8S} \
                    //     """cd repos/project_lib_deploy/yaml; \
                    //     export BRANCH=${BRANCH}; \
                    //     export TAG=${TAG}; \
                    //     export SERVICE=${SERVICE}; \
                    //     kubectl create namespace ${BRANCH}; \
                    //     kubectl apply -f issuer.yaml; \
                    //     envsubst < ingress.yaml | kubectl apply -f -; \
                    //     kubectl delete -n ${BRANCH} secret regcred --ignore-not-found; \
                    //     kubectl create secret docker-registry regcred \
                    //             --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com \
                    //             --docker-username=AWS \
                    //             --docker-password=$(aws ecr get-login-password --region ${AWS_REGION}) \
                    //             --namespace=${BRANCH}; \
                    //     envsubst < service-${SERVICE}.yaml | kubectl apply -f -; \
                    //     kubectl delete deploy deploy-${SERVICE} -n ${BRANCH}; \
                    //     envsubst < deploy-${SERVICE}.yaml | kubectl apply -f -;"""' 
                    }                                             
                }                                                       
            }
        } 
    }
}