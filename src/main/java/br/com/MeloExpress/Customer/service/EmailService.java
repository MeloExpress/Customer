package br.com.MeloExpress.Customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.util.Date;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailService (JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "routing-event", groupId = "customer-api")
    public void receiveMessage(GenericMessage<String> message) throws IOException, ParseException {
        String payload = message.getPayload();
        String mensagemJson = payload.substring(payload.indexOf("{"));

        // Criar um objeto JSONObject a partir do payload
        JSONObject mensagem = new JSONObject(mensagemJson);

        // Extrair os dados do objeto JSONObject
        JSONArray collectDetailsList = mensagem.getJSONArray("collectDetailsList");
        String fleetDetails = mensagem.getString("fleetDetails");
        String employeeDetails = mensagem.getString("employeeDetails");

        // Extrair informações do fleet
        String fleetDetailsJson = fleetDetails.substring(fleetDetails.indexOf("[") + 1, fleetDetails.indexOf("]"));
        String[] fleetParts = fleetDetailsJson.split(", ");

        String fleetNumber = fleetParts[0].split("=")[1];
        String fleetCode = fleetParts[1].split("=")[1];
        String fleetPlate = fleetParts[2].split("=")[1];

        // Extrair informações do employee
        String employeeDetailsJson = employeeDetails.substring(employeeDetails.indexOf("[") + 1, employeeDetails.indexOf("]"));
        String[] employeeParts = employeeDetailsJson.split(", ");

        int employeeId = Integer.parseInt(employeeParts[0].split("=")[1]);
        String employeeCode = employeeParts[1].split("=")[1];
        String employeeName = employeeParts[2].split("=")[1];
        String employeeCpf = employeeParts[3].split("=")[1];

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm 'hs'");

        // Iterar sobre os detalhes de coleta
        for (int i = 0; i < collectDetailsList.length(); i++) {
            JSONObject collectDetails = collectDetailsList.getJSONObject(i);

            String email = collectDetails.getString("email");
            String responsible = collectDetails.getString("responsible");
            String startTimeString = collectDetails.getString("startTime");
            String endTimeString = collectDetails.getString("endTime");
            String street = collectDetails.getString("street");
            String number = collectDetails.getString("number");
            String district = collectDetails.getString("district");
            String city = collectDetails.getString("city");

            // Formatar a data e hora de início
            Date startTime = inputDateFormat.parse(startTimeString);
            String formattedStartTime = outputDateFormat.format(startTime);

            // Formatar a data e hora de término
            Date endTime = inputDateFormat.parse(endTimeString);
            String formattedEndTime = outputDateFormat.format(endTime);

            // Criar a mensagem de e-mail
            String subject = "Sua coleta está a caminho!";
            String body = "Olá " + responsible + ", espero que esteja tudo bem!\n\n" +
                    "O motorista " + employeeName + " já está a caminho para realizar a sua coleta" + ".\n\n" +
                    "no caminhão de placa: " + fleetPlate + ".\n\n" +
                    "Peço que nos aguarde dentro do horário combinado.\n\n" +
                    " Das: " + formattedStartTime + " Até: " + formattedEndTime + "\n\n" +
                    "No seguinte endereço: \n\n" +
                    street + ", " + number + ", " + district + ", " + city + ".\n\n" +
                    "Desde já agradecemos pela preferência.\n\n" +
                    "Atenciosamente,\n\n" +
                    "Equipe MeloExpress.";


            // Enviar o e-mail
            enviarEmail(email, subject, body);
        }
    }


    private void enviarEmail(String email, String subject, String body) {
        // Configurar e enviar o e-mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("meloexpresslog@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }


}

