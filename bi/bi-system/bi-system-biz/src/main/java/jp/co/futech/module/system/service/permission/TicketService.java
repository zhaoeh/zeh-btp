package jp.co.futech.module.system.service.permission;

import org.springframework.ui.Model;

import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 * @author MYK-Heng.zhang
 */
public interface TicketService {

    public Map<String,String> getTicket() throws ExecutionException, InterruptedException;


}
