#!/usr/bin/env groovy
import com.Constants

def call(String branch, String tag, String service, String build_num) {
    def REPOSITORY_URI="${Constants.AWS_ACCOUNT_ID}.dkr.ecr.${Constants.AWS_REGION}.amazonaws.com/${Constants.IMAGE_REPO_NAME}_${service}_${branch}"
    def REPO_NAME="${Constants.IMAGE_REPO_NAME}_${service}_${branch}"
    MANIFEST_TMP=sh(script: "aws ecr batch-get-image --repository-name ${REPO_NAME} --image-ids imageTag=latest \
        --region ${Constants.AWS_REGION} --query 'images[0].imageManifest' --output json",returnStdout: true)
    if ( MANIFEST_TMP != null & MANIFEST_TMP != "null" & MANIFEST_TMP != "" ) {
        MANIFEST="${MANIFEST_TMP}".replace('\\n', '')
//    if ("${MANIFEST}" != "null") {
        sh(script: "aws ecr put-image --repository-name ${REPO_NAME} --image-tag ${build_num} --region \
            ${Constants.AWS_REGION} --image-manifest ${MANIFEST}")
        sh(script: "aws ecr batch-delete-image --repository-name ${REPO_NAME} --region ${Constants.AWS_REGION} \
            --image-ids imageTag=latest")
    }
   sh "docker build src/ -t ${REPOSITORY_URI}:${tag}"
   sh "docker push ${REPOSITORY_URI}:${tag}"
}