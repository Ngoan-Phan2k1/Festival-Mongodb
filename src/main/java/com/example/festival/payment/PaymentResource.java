package com.example.festival.payment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.festival.booktour.BookedTour;
import com.example.festival.exception.PaymentException;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentResource {

    @Autowired
    private PaymentService paymentService;
    
    @GetMapping("/create_payment")
    public ResponseEntity<?> creatPayment(
        //@PathVariable Integer bookedtourId,
        @RequestParam(value = "amount") String amount,
        @RequestParam(value = "bookedtourId") Integer bookedtourId

        ) throws UnsupportedEncodingException {

        String orderType = "other";
        // long amount = Integer.parseInt(req.getParameter("amount"))*100;
        // String bankCode = req.getParameter("bankCode");

        long amountValue = Long.parseLong(amount);
        amountValue = amountValue * 100;
        
        
        String vnp_TxnRef = Config.getRandomNumber(8);
        //String vnp_IpAddr = Config.getIpAddress(req);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = Config.vnp_TmnCode;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountValue));
        vnp_Params.put("vnp_CurrCode", "VND");
        //vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", "vn");
        
        // if (bankCode != null && !bankCode.isEmpty()) {
        //     vnp_Params.put("vnp_BankCode", bankCode);
        // }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        //vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", Integer.toString(bookedtourId));
        vnp_Params.put("vnp_OrderType", orderType);

        //String locate = req.getParameter("language");
        // if (locate != null && !locate.isEmpty()) {
        //     vnp_Params.put("vnp_Locale", locate);
        // } else {
        //     vnp_Params.put("vnp_Locale", "vn");
        // }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        // com.google.gson.JsonObject job = new JsonObject();
        // job.addProperty("code", "00");
        // job.addProperty("message", "success");
        // job.addProperty("data", paymentUrl);
        // Gson gson = new Gson();
        // resp.getWriter().write(gson.toJson(job));

        //PaymentDTO paymentDTO = new PaymentDTO("OK", "Success", paymentUrl);
        PaymentResponse paymentResponse = new PaymentResponse(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }


    @GetMapping("/payment_infor")
    public ResponseEntity<?> transaction(
    //    @RequestParam(value = "vnp_Amount") String amount,
    //     @PathVariable Integer bookedtourId,
    //     // @RequestParam(value = "vnp_BankCode") String bankCode,
    //     // @RequestParam(value = "vnp_OrderInfo") String order,
    //    @RequestParam(value = "vnp_ResponseCode") String responseCode

        @RequestParam Map<String, String> queryParams
    ) {
        
        String vnp_OrderInfo = queryParams.get("vnp_OrderInfo");
        String vnp_Amount = queryParams.get("vnp_Amount");
        String vnp_TxnRef = queryParams.get("vnp_TxnRef");
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        Integer bookedtourId = Integer.parseInt(vnp_OrderInfo);

        Map fields = new HashMap();

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            // if (fieldValue != null && fieldValue.length() > 0 ) {
            //     System.out.println("Name: " + fieldName + " , " + "Value: " + fieldValue);
            // }
            fields.put(fieldName, fieldValue);
        }

        String vnp_SecureHash = queryParams.get("vnp_SecureHash"); //mã hash để so sánh đầu vào

        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) 
        {
            fields.remove("vnp_SecureHash");
        }
        String signValue = Config.hashAllFields(fields);

        if (signValue.equals(vnp_SecureHash)) {
            if (vnp_ResponseCode.equals("00")) {

                BookedTour bookedTour = new BookedTour(bookedtourId, null, null, null, null, null, null, null, null, null, null, null, null, null);
                Integer amount = Integer.parseInt(vnp_Amount);
                Payment payment = new Payment(amount/100, vnp_TxnRef, bookedTour);
                Payment paymentDB = paymentService.add(payment);
                return ResponseEntity.status(HttpStatus.OK).body(paymentDB);

            }
            throw new PaymentException("Giao dịch không thành công");
           
        }
        throw new PaymentException("Giao dịch không thành công");

    }
    
}
