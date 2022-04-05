package com

class Constants {
    static final String IP_K8S="16.170.42.2"
    static final String AWS_ACCOUNT_ID="728490037630"
    static final String AWS_REGION="eu-north-1" 
    static final String IMAGE_REPO_NAME="bigproject"
    static final String BRANCH=BRANCH_NAME
    static final String REPOSITORY_URI="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_REPO_NAME}_nginx_${BRANCH}"
    static final String IMAGE_TAG=GIT_COMMIT
}