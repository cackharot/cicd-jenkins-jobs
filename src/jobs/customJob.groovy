import org.cackharot.jenkins.dsl.custom.JobDslParser

new JobDslParser().load("src/jobs/custom").build(this)