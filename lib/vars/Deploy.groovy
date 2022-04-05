#!/usr/bin/env groovy
import Constants

def call() {//(String branch, String tag, String service) {
  // if ("${BRANCH_DEV}" == 'develop') {
  //     this.script.sh('ssh ubuntu@${IP_K8S} \
  //     """cd repos/project_lib_deploy/yaml; \
  //     export BRANCH=${branch}; \
  //     export TAG=${tag}; \
  //     export SERVICE=${service}; \
  //     kubectl create namespace ${branch}; \
  //     kubectl apply -f issuer.yaml; \
  //     envsubst < ingress.yaml | kubectl apply -f -; \
  //     kubectl delete -n ${branch} secret regcred --ignore-not-found; \
  //     kubectl create secret docker-registry regcred \
  //             --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com \
  //             --docker-username=AWS \
  //             --docker-password=$(aws ecr get-login-password --region ${AWS_REGION}) \
  //             --namespace=${branch}; \
  //     envsubst < service-${service}.yaml | kubectl apply -f -; \
  //     kubectl delete deploy deploy-${service} -n ${branch}; \
  //     envsubst < deploy-${service}.yaml | kubectl apply -f -;"""')
  // } else {
    //echo "${tag}"
      this.script.sh("aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS \
      --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com")
      //   'ssh ubuntu@${IP_K8S} \
      // """cd repos/project_lib_deploy/yaml; \
      // export BRANCH=${branch}; \
      // export TAG=${tag}; \
      // export SERVICE=${service}; \
      // kubectl create namespace ${branch}; \
      // kubectl apply -f issuer.yaml; \
      // envsubst < ingress.yaml | kubectl apply -f -; \
      // kubectl delete -n ${branch} secret regcred --ignore-not-found; \
      // kubectl create secret docker-registry regcred \
      //         --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com \
      //         --docker-username=AWS \
      //         --docker-password=$(aws ecr get-login-password --region ${AWS_REGION}) \
      //         --namespace=${branch}; \
      // envsubst < service-${service}.yaml | kubectl apply -f -; \
      // kubectl delete deploy deploy-${service} -n ${branch}; \
      // envsubst < deploy-${service}.yaml | kubectl apply -f -;"""')
   // }
}