package ch.hesge.cours634.svc;

import ch.hesge.cours634.webservice.FilterAuth;
import org.apache.log4j.Logger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;

@Interceptor
@Log
@Priority(Interceptor.Priority.APPLICATION)
public class LogInterceptor {

    Logger logger = Logger.getLogger(LogInterceptor.class);
    @AroundInvoke
    @AroundTimeout
    public Object logMethodEntry(InvocationContext ctx) throws Exception {

        logger.info("méthode invoqué"+ctx.getMethod().getName());
        logger.info(">>>>>>>>les paramètres");
        Arrays.stream(ctx.getParameters()).forEach(e-> logger.info(e.toString()));
        Object res=ctx.proceed();
        logger.info("valeur de retour"+res.toString());


        /*long start = System.currentTimeMillis();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>><Entering method: " + ctx.getMethod().getName());
        Object res = ctx.proceed();
        long end = System.currentTimeMillis();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>><Exiting method: " + ctx.getMethod().getName() + " en "+ (end-start) +" ms") ;
        return res;*/
        return res;
    }
}
