package org.cackharot.jenkins.dsl.custom

import org.cackharot.jenkins.dsl.model.JobType

class JobTypeFactory {
  static def jobImpls = new HashMap<JobType, Class<JobImpl>>()
  static {
    register(JobType.BUILD, BuildJobImpl.class)
    register(JobType.DEPLOY, DeployJobImpl.class)
  }

  static def register(final JobType jobType, final Class<JobImpl> jobImpl) {
    jobImpls.put(jobType, jobImpl)
  }

  static JobImpl getImpl(final Map jobModel) {
    def jobType = Enum.valueOf(JobType.class, String.valueOf(jobModel.type?.trim()?.toUpperCase()))
    if (jobImpls.containsKey(jobType)) {
      def jobImplClass = jobImpls.get(jobType)
      return jobImplClass.newInstance(jobModel)
    }
    throw new UnknownJobTypeException(jobModel.name, jobModel.type)
  }
}

