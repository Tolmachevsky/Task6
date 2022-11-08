<?php

require_once('mail.PHP');
$mail = new PHPMailer;
$mail->CharSet = 'utf-8';

$name = $_POST['name'];
$email = $_POST['email'];

//$mail->SMTPDebug = 3;                               // Enable verbose debug output

$mail->isSMTP();                                      // Set mailer to use SMTP
$mail->Host = 'smtp.mail.ru';  																							// Specify main and backup SMTP servers
$mail->SMTPAuth = true;                               // Enable SMTP authentication
$mail->Username = 'turik.petrov.21@mail.ru'; // Ваш логин от почты с которой будут отправляться письма
$mail->Password = '1COoBI3tepi-'; // Ваш пароль от почты с которой будут отправляться письма
$mail->SMTPSecure = 'ssl';                            // Enable TLS encryption, `ssl` also accepted
$mail->Port = 465; // TCP port to connect to / этот порт может отличаться у других провайдеров

$mail->setFrom('turik.petrov.21@mail.ru'); // от кого будет уходить письмо?
$mail->addAddress('bananer672@fgvod.com');     // Кому будет уходить письмо
//$mail->addAddress('ellen@example.com');               // Name is optional
//$mail->addReplyTo('info@example.com', 'Information');
//$mail->addCC('cc@example.com');
//$mail->addBCC('bcc@example.com');
//$mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
//$mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name
$mail->isHTML(true);                                  // Set email format to HTML

$mail->Subject = 'Заявка с тестового сайта';
$mail->Body    = '' .$name . ' оставил заявку, его почтовый ящик ' .$mail.'.';
$mail->AltBody = '';

if(!$mail->send()) {
    echo 'Error';
} else {
    header('location: thank-you.html');
}
?>

File file = new File("C:\\JavaProjects\\Task6 — 2.0\\src\\main\\resources\\mail.properties");
Properties properties = new Properties();
properties.load(new FileReader(file));

User user = userService.findById(id);

Session session = Session.getDefaultInstance(properties);
MimeMessage message = new MimeMessage(session);
message.setFrom(user.getEmail());
message.addRecipients(Message.RecipientType.TO,"njk.petrov@ya.ru");
message.setSubject("admin role");
message.setText("ahahah");

Transport transport = session.getTransport();
transport.connect(null,"Turik228!");
transport.sendMessage(message, message.getAllRecipients());
transport.close();
