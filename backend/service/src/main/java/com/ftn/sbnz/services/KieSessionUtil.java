package com.ftn.sbnz.services;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class KieSessionUtil {

    	public static void removeFromSessionByClass(KieSession kieSession, Class<?> clazz) {

		for (FactHandle factHandle : kieSession.getFactHandles()) {

			Object object = kieSession.getObject(factHandle);
			
			if (object != null && clazz.isAssignableFrom(object.getClass())) {
				kieSession.delete(factHandle);
			}
		}
	}
}
