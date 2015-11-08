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
	      //set hook url
	      sc.setWebhookUrl("");
	      return sc;
	  }

    @Override
    public void configure() {    	
    	TwitterComponent tc = getContext().getComponent("twitter", TwitterComponent.class);
    	//set credentials
    	tc.setAccessToken("");
    	tc.setAccessTokenSecret("");
    	tc.setConsumerKey("");
    	tc.setConsumerSecret("");
    	
    	
    	from("twitter://streaming/filter?type=EVENT&delay=2&keywords=#openfest15")
    		.streamCaching()
    		.to("slack:#random","log:out");
    }

}
