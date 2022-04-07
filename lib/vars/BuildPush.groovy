#!/usr/bin/env groovy
import com.Constants

def call(String branch, String tag, String service, String build_num) {
    def REPOSITORY_URI="${Constants.AWS_ACCOUNT_ID}.dkr.ecr.${Constants.AWS_REGION}.amazonaws.com/${Constants.IMAGE_REPO_NAME}_${service}_${branch}"
    def REPO_NAME="${Constants.IMAGE_REPO_NAME}_${service}_${branch}"
    MANIFEST=sh(script: "aws ecr batch-get-image --repository-name ${REPO_NAME} --image-ids imageTag=latest --region ${Constants.AWS_REGION} --query images[].imageManifest --output text").replace('\\n', '')
    sh(script: "aws ecr put-image --repository-name ${REPO_NAME} --image-tag ${build_num} --image-manifest ${MANIFEST} --region ${Constants.AWS_REGION}")
    sh(script: "aws ecr batch-delete-image --repository-name ${REPO_NAME} --image-ids imageTag=latest")
    sh "docker build src/ -t ${REPOSITORY_URI}:latest"
    sh "docker push ${REPOSITORY_URI}:latest"
}