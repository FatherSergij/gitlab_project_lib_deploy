pipeline {
    agent any
    // environment {
    //     IP_K8S="16.170.42.2"
    //     AWS_ACCOUNT_ID="728490037630"
    //     AWS_REGION="eu-north-1"
    //     BRANCH="${params.BranchRun}"
    //     TAG="${params.ImageTag}" 
    //     SERVICE="${params.ServiceRun}"       
    //     BRANCH_DEV="${params.BranchRun_dev}"
    //     TAG_DEV="${params.ImageTag_dev}" 
    //     SERVICE_DEV="${params.ServiceRun_dev}"        
    // }    
    
    libraries {
         lib('lib-for-project')
    }    
    
    stages {       

        stage('Logging into AWS ECR') {
            steps {
                script {
                    LogToEcr
                //    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
                }
            }
        }


        stage('Deploy on k8s from nginx-phpfpm') {

            steps {
          //sh scp -o StrictHostKeyChecking=no -r repos/project_lib_deploy/ ubuntu@${IP_K8S}:~/"
                script {
                    Deploy
                }                                                    
            }
        } 
    }
}