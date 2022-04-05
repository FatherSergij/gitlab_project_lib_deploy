#!/usr/bin/env groovy
import com.Constants

def call(String branch_dep, String tag_dep, String service_dep) {
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
      sh("ssh ubuntu@${Constants.IP_K8S} 'export BRANCH=${branch_dep}')
      // """cd repos/project_lib_deploy/yaml; \
      // export BRANCH=${branch_dep}; \
      // export TAG=${tag_dep}; \
      // export SERVICE=${service_dep}; \
      // kubectl create namespace ${branch_dep}; \
      // kubectl apply -f issuer.yaml; \
      // envsubst < ingress.yaml | kubectl apply -f -; \
      // kubectl delete -n ${branch} secret regcred --ignore-not-found; \
      // kubectl create secret docker-registry regcred \
      //         --docker-server=${Constants.AWS_ACCOUNT_ID}.dkr.ecr.${Constants.AWS_REGION}.amazonaws.com \
      //         --docker-username=AWS \
      //         --docker-password=$(aws ecr get-login-password --region ${Constants.AWS_REGION}) \
      //         --namespace=${branch}; \
      // envsubst < service-${service}.yaml | kubectl apply -f -; \
      // kubectl delete deploy deploy-${service} -n ${branch}; \
      // envsubst < deploy-${service}.yaml | kubectl apply -f -;"""')
   //}
}