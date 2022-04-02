pipeline {
    agent any
    environment {
        IP_K8S="16.170.42.2"
        AWS_ACCOUNT_ID="728490037630"
        AWS_REGION="eu-north-1"
        //BRANCH="develop" 
    }    
    
    parameters {
        string defaultValue: '', description: 'K', name: 'BRANCHBUILD'
    }    
    
    stages {       

         stage('Logging into AWS ECR') {
            steps {
                script {
                sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
                }
                    echo "${params.BRANCHBUILD}"
            }
        }          

        stage('Deploy on k8s from nginx-phpfpm') {
              steps {
                //sh "scp -o StrictHostKeyChecking=no -r yaml/ ubuntu@${IP_K8S}:~/"
                script {
                sh 'ssh ubuntu@${IP_K8S} \
                    """cd repos/project_lib_deploy; \
                   export BRANCH="${params.BRANCHBUILD}"; \
                   export TAG=$params.TAG; \
                   echo $BRANCH; \
                   echo $TAG; \
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
                   kubectl delete deploy deploy-dev1 -n ${BRANCH}; \
                   envsubst < deploy-nginx-phpfpm.yaml | kubectl apply -f -;"""'                            
                }
            }
        } 
    }
}