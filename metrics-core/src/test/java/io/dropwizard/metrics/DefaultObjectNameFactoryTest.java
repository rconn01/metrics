package io.dropwizard.metrics;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.junit.Before;
import org.junit.Test;


public class DefaultObjectNameFactoryTest {
	
	private ObjectNameFactory factory;
	private Map<String,String> tags;
	
	@Before
	public void setup() {
		factory = new DefaultObjectNameFactory();
		tags = new HashMap<>();
		tags.put("k1", "v1");
		tags.put("k3", "v3");
		
	}

	@Test
	public void createsObjectNameWithDomainInInput() throws MalformedObjectNameException {
		ObjectName on = factory.createName("type", "com.domain", MetricName.build("something.with.dots"));
		assertThat(on.getDomain()).isEqualTo("com.domain");
	}

	@Test
	public void createsObjectNameWithNameAsKeyPropertyName() throws MalformedObjectNameException {
		ObjectName on = factory.createName("type", "com.domain", MetricName.build("something.with.dots"));
		assertThat(on.getKeyProperty("name")).isEqualTo("something.with.dots");
	}
	
	@Test
	public void createsObjectWithTags() throws MalformedObjectNameException {		
		MetricName name = new MetricName("a.name",tags);
		ObjectName on = factory.createName("meter", "metrics", name);
		
		assertThat(on.getDomain()).isEqualTo("metrics");
		assertThat(on.getKeyProperty("name")).isEqualTo("a.name");
		assertThat(on.getKeyProperty("k1")).isEqualTo("v1");
		assertThat(on.getKeyProperty("k3")).isEqualTo("v3");		
	}
	
	@Test
	public void createsQuotedObjectNames() throws MalformedObjectNameException {
		tags.put("test", "*");
		MetricName name = new MetricName("a.name",tags);
		ObjectName on = factory.createName("meter", "metrics", name);
		
		assertThat(on.isPattern()).isFalse();
		assertThat(on.getKeyProperty("test")).isEqualTo(ObjectName.quote("*"));
		
		name = new MetricName("a.name.?");
		on = factory.createName("meter", "metrics", name);
		
		assertThat(on.isPattern()).isFalse();
		assertThat(ObjectName.unquote(on.getKeyProperty("name"))).isEqualTo("a.name.?");
	}
}
