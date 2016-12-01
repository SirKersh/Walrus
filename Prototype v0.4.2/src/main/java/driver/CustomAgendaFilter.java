package driver;
import java.util.List;

import org.drools.core.marshalling.impl.ProtobufMessages.Activation;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

public class CustomAgendaFilter implements AgendaFilter {

	private final List<String> ruleNamesThatAreAllowedToFire;

	public CustomAgendaFilter(List<String> ruleNamesThatAreAllowedToFire) {
		this.ruleNamesThatAreAllowedToFire = ruleNamesThatAreAllowedToFire;
	}

	@Override
	public boolean accept(Match arg0) {
		System.out.println();
		System.out.println(arg0.getRule().getName());
		System.out.println();
		return ruleNamesThatAreAllowedToFire.contains(arg0.getRule().getName());
	}

}
