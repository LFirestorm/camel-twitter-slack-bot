package net.lf.openfest;

import org.apache.camel.component.slack.SlackComponent;
import org.apache.camel.component.twitter.TwitterComponent;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class MySpringBootRouter extends FatJarRouter {
	
	 @Bean(name = "slack")
	  public SlackComponent getSomeClass() {
	      SlackComponent sc = new SlackComponent();
	      return sc;
	  }

    @Override
    public void configure() {
        /*from("timer://trigger").
                transform().simple("ref:myBean").
                to("slack:#random", "log:out", "mock:test");*/
    	
    	TwitterComponent tc = getContext().getComponent("twitter", TwitterComponent.class);
    	    	
    	
    	from("twitter://streaming/filter?type=EVENT&delay=2&keywords=#openfest15")
    		.streamCaching()
    		.to("slack:#random","log:out");
    }

    @Bean
    String myBean() {
        return "I'm Spring bean!";
    }

}
