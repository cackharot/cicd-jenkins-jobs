package org.cackharot.jenkins.dsl.model

class JobBaseModel {
  String name
  String description
  JobType type
  List<String> triggers
}

class BuildJobModel extends JobBaseModel{
  String repoName
  String repoBaseUrl
}

class DeployJobModel extends JobBaseModel {
  List<DeployEnvironment> environments = new ArrayList<>()
}

class DeployEnvironment {
  String name
}
