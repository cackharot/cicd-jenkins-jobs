package org.cackharot.jenkins.dsl.model

class JobBaseModel {
  String name
  String description
  JobType type
  List<String> triggers
}