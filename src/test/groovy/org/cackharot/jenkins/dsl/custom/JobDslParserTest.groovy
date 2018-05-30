package org.cackharot.jenkins.dsl.custom

import spock.lang.Specification


class JobDslParserTest extends Specification {
    void testLoad() {
        given:
        def parser = new JobDslParser()

        when:
        parser.load("./src/jobs/custom")

        then:
        noExceptionThrown()
    }
}
