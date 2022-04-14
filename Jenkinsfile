pipeline {
    agent any
    environment {
        BRANCHNG="${params.BranchRunNginx}"
        TAGNG="${params.ImageTagNginx}" 
        SERVICENG="nginx"  
        BRANCHND="${params.BranchRunNode}"
        TAGND="${params.ImageTagNode}" 
        SERVICEND="node"
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


        stage('Deploy on k8s') {

            steps {
                script {
                    if ("${BRANCH_DEV}" == 'develop') {
                        Deploy ("${BRANCH_DEV}", "${TAG_DEV}", "${SERVICE_DEV}")
                    } else {
                        DeployHelm ("${BRANCHNG}", "${TAGNG}", "${SERVICENG}", "${BRANCHND}", "${TAGND}", "${SERVICEND}")
                    }                                             
                }                                                       
            }
        } 
    }
}