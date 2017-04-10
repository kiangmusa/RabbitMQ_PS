/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.rabbitmq.client.BuiltinExchangeType;
import java.io.IOException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author kiangmusa
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        FileReader fr = new FileReader("C:\\xampp\\apache\\logs\\access.log");
        BufferedReader br = new BufferedReader(fr);

        while (true) {
            String line = br.readLine();
            if (line == null) {
                Thread.sleep(1 * 1000);
            } else {
                channel.basicPublish(EXCHANGE_NAME, "", null, line.getBytes());
                System.out.println(" [x] Sent '" + line + "'");
                System.out.println(line);
            }
        }

    }

}
