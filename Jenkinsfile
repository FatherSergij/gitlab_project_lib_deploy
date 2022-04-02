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
        string defaultValue: '', description: 'G', name: 'TAGBUILD'
    }    
    
    stages {       

        stage('Logging into AWS ECR') {
            steps {
                script {
                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
                }
            }
        }          

        stage('Setup parameters') {
            steps {
                script {
                    properties([
                      parameters([
                        [$class: 'CascadeChoiceParameter', 
                          choiceType: 'PT_SINGLE_SELECT', 
                          description: 'Select Image',
                          filterLength: 1,
                          filterable: false,
                          name: 'TAGBUILD', 
                          script: [
                            $class: 'GroovyScript', 
                            script: [
                              classpath: [], 
                              sandbox: false, 
                              script: 
                                '''
                                import com.amazonaws.client.builder.AwsClientBuilder;
                                import com.amazonaws.services.ecr.AmazonECR;
                                import com.amazonaws.services.ecr.AbstractAmazonECR;
                                import com.amazonaws.services.ecr.AmazonECRClient;
                                import com.amazonaws.services.ecr.model.ListImagesRequest;
                                import com.amazonaws.services.ecr.model.ListImagesResult;
                                import com.amazonaws.services.ecr.AmazonECRClientBuilder;
                                import com.amazonaws.regions.Region;
                                import com.amazonaws.regions.RegionUtils;
                                import com.amazonaws.regions.Regions;
                                import jenkins.model.*

                                AmazonECR client = AmazonECRClientBuilder.standard().withRegion("${AWS_REGION}").build();
                                ListImagesRequest request = new ListImagesRequest().withRepositoryName("bigproject");
                                res = client.listImages(request);


                                def result = []
                                for (image in res) {
                                   result.add(image.getImageIds());
                                }

                                return result[0].imageTag;
                                '''
                            ]
                          ]
                        ]
                      ])
                    ])
                }
            }
        }
                    

        stage('tag') {
            steps {
                script {
                   sh "aws ecr list-images --region ${AWS_REGION} --repository-name bigproject --output text"
                }
            }
        }

        //stage('Deploy on k8s from nginx-phpfpm') {
        //    environment {
        //        BRANCH="${params.BRANCHBUILD}"
        //        TAG="${params.TAGBUILD}"
        //    }            
        //      steps {
                //sh "scp -o StrictHostKeyChecking=no -r repos/project_lib_deploy/ ubuntu@${IP_K8S}:~/"
        //        script {
        //        sh 'ssh ubuntu@${IP_K8S} \
        //            """cd repos/project_lib_deploy; \
        //           kubectl create namespace ${BRANCH}; \
        //           export BRANCH=${BRANCH}; \
        //           export TAG=${TAG}; \
        //           kubectl apply -f issuer.yaml; \
        //           envsubst < ingress.yaml | kubectl apply -f -; \
        //           kubectl delete -n ${BRANCH} secret regcred --ignore-not-found; \
        //           kubectl create secret docker-registry regcred \
        //                   --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com \
        //                   --docker-username=AWS \
        //                   --docker-password=$(aws ecr get-login-password --region ${AWS_REGION}) \
        //                   --namespace=${BRANCH}; \
        //           envsubst < service-nginx-phpfpm.yaml | kubectl apply -f -; \
        //           kubectl delete deploy deploy-dev1 -n ${BRANCH}; \
        //           envsubst < deploy-nginx-phpfpm.yaml | kubectl apply -f -;"""'                           
        //        }
        //    }
        //} 
    }
}