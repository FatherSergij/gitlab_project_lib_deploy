pipeline {
    agent any
    environment {
        BRANCH="${params.BranchRun}"
        TAG="${params.ImageTag}" 
        SERVICE="${params.ServiceRun}"       
        BRANCH_DEV="${params.BranchRun_dev}"
        TAG_DEV="${params.ImageTag_dev}" 
        SERVICE_DEV="${params.ServiceRun_dev}"        
    }    
    
    libraries {
         lib('lib-for-project')
    }    
    
    stages {       

        stage('Logging into AWS ECR') {
            steps {
                script {
                    LogToEcr()
                }
            }
        }


        stage('Deploy on k8s from nginx-phpfpm') {

            steps {
                script {
                    if ("${BRANCH_DEV}" == 'develop') {
                        Deploy ("${BRANCH_DEV}", "${TAG_DEV}", "${SERVICE_DEV}")
                    } else {
                        Deploy ("${BRANCH}", "${TAG}", "${SERVICE}")
                    }                                             
                }                                                       
            }
        } 
    }
}