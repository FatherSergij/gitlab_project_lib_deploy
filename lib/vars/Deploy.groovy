#!/usr/bin/env groovy
import Constants

def call() {
  if ("${BRANCH_DEV}" == 'develop') {
      sh 'ssh ubuntu@${IP_K8S} \
      """cd repos/project_lib_deploy/yaml; \
      export BRANCH=${BRANCH_DEV}; \
      export TAG=${TAG_DEV}; \
      export SERVICE=${SERVICE_DEV}; \
      kubectl create namespace ${BRANCH_DEV}; \
      kubectl apply -f issuer.yaml; \
      envsubst < ingress.yaml | kubectl apply -f -; \
      kubectl delete -n ${BRANCH_DEV} secret regcred --ignore-not-found; \
      kubectl create secret docker-registry regcred \
              --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com \
              --docker-username=AWS \
              --docker-password=$(aws ecr get-login-password --region ${AWS_REGION}) \
              --namespace=${BRANCH_DEV}; \
      envsubst < service-${SERVICE_DEV}.yaml | kubectl apply -f -; \
      kubectl delete deploy deploy-${SERVICE_DEV} -n ${BRANCH_DEV}; \
      envsubst < deploy-${SERVICE_DEV}.yaml | kubectl apply -f -;"""'
  } else {
      sh 'ssh ubuntu@${IP_K8S} \
      """cd repos/project_lib_deploy/yaml; \
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
      envsubst < service-${SERVICE}.yaml | kubectl apply -f -; \
      kubectl delete deploy deploy-${SERVICE} -n ${BRANCH}; \
      envsubst < deploy-${SERVICE}.yaml | kubectl apply -f -;"""' 
  }
}