$("#signup-form").validate({
    rules: {
        full_name: {
            required: true,
            pattern: /^[a-zA-Z]{2,15}\s[a-zA-Z]{2,15}$/,
        },
        contact: {
            required: true,
            pattern: /^[0-9]{3}\-[0-9]{3}\-[0-9]{4}$/
        },
        email: {
            required: true,
            email: true
        },
        dob: {
            required: true,
            // pattern: '[0-3]{1}[0-9]{1}-[0-1]{}
        },
        address: {
            required: true,
//            maxLength: 25
        },
        city: {
            required: true,
        },
        state: {
            required: true
        },
        zip: {
            required: true,
            pattern: /^[0-9]{5}$/
        },
        ssn: {
            required: true,
            pattern: /^[0-9]{3}\-[0-9]{2}\-[0-9]{4}$/
        }
    },
    messages: {
      contact: "Please follow the correct pattern...XXX-XXX-XXXX",
      zip: "Invalid ZIP Code length",
      ssn: "Invalid format XXX-XX-XXXX"
    },
//    success: function() {
//        console.log("success");
//    }
  });

  $("#login-form").validate({
      rules: {
        email: {
            required: true
        },
        password: {
            required: true,
            pattern: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$/
        }
      },
      messages: {
    	  email: "Invalid email address",
    	  password: "Invalid email or password"
      }
  });

$("#securityStuff").validate({
    rules: {
        password: {
            required: true,
            pattern: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$/
        },
        passConfirm: {
            required: true,
            equalTo: password
        },
        pin: {
            required: true,
            pattern: /^[0-9]{4}$/
        },
        sqanswer: {
            required: true,
            pattern: /^[a-z]{1,20}$/
        } 
    },
    messages: {
        passsword: "Invalid format",
        passConfirm: "Passwords do not match",
        pin: "Invalid PIN",
        sqanswer: "Answer must be only characters, limit answer to 20 characters"
    }
})