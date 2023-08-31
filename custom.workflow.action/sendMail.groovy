// Demo Code for send email with script

if(beforeEvent) return

import com.intland.codebeamer.mail.MailMessage

def smtp = new MailMessage()
smtp.setFrom(user)
smtp.setSubject("just demo send from script")
smtp.setText("Email Text", true)
smtp.addTo(user.email)

smtp.send()



