// quick-switch between 'forgot password' modal
$("#switch-modals").click(function(){
    $('#pw-reset1').modal('hide');
    $('#pw-reset2').modal('show');
})


$("#continue_submit").click(function(){
	event.preventDefault();
	console.log("hit")
    if($("#signup-form").valid()){
    	console.log("valid")
        $('#set-security').modal('show');
    } else {
    	console.log("invalid")
    	// $('#set-security').modal('show');
    }
})

var pageData;

// login fetch call
function loginCall(){
	event.preventDefault();
    var user = document.getElementById("user-field").value;
    var pass = document.getElementById("password-field").value;
    console.log("about to fetch")
    if($("#login-form").valid()){
    fetch("http://localhost:4002/project-1/login", {
            method: 'POST',
            body: JSON.stringify({
            	userName: user,
            	passWord: pass
            })
        })
        .then(res => {
            return res.json();
        }).then(data => {
            pageData = data;
            console.log(pageData);
            if(data.acctId!=""){
                window.location.href = "http://localhost:4002/project-1/home.html";
                console.log("End of if statement")
            } else {
                document.getElementById("invalidBox").innerText = "Invlaid email or password"
            }

        })
        
    }
}


function pageSetup(data) {
    console.log("Page data: "+pageData);
    console.log("First name: ")
        console.log(data.firstName);
        document.getElementById("nameBox").innerText = data.firstName;
}

function pullUserData(){
     event.preventDefault();
     console.log("Pull User Details");
     fetch("http://localhost:4002/project-1/home")
     .then(res => {
    	 console.log("Hello from res")
         return res.json();
     })
     .then(data => {
         console.log("First name: ")
         console.log(data.firstName);
         document.getElementById("nameBox").innerHTML = "Welcome, "+data.firstName;
     }) 
 }

 function signupUser(){
     console.log("signup called");
     var firstName = document.getElementById("full_name_id").value.split(" ")[0];
     var lastName = document.getElementById("full_name_id").value.split(" ")[1];
     var contact = document.getElementById("contact_id").value;
     var email = document.getElementById("email_id").value;
     var dob = document.getElementById("dob").value;
     var address = document.getElementById("address").value;
     var city = document.getElementById("city_id").value;
     var state = document.getElementById("state_id").value;
     var zip = document.getElementById("zip_id").value;
     var ssn = document.getElementById("ssn_id").value;
     var pass = document.getElementById("password").value;
     var pin = document.getElementById("pin").value;
     var sq = document.getElementById("sqanswer").value;
     if($("#securityStuff").valid()){
     fetch("http://localhost:4002/project-1/signup", {
         method: "POST",
         body: JSON.stringify({
             acctId: 1234,
             userName: email,
             passWord: pass,
             pin: pin,
             firstName: firstName,
             lastName: lastName,
             contact: contact,
             email: email,
             address: address,
             city: city,
             state: state,
             zip: zip,
             dob: dob,
             ssn: ssn,
             sq: sq
         })
     })
     .then(res => {
        return res.json();
    }).then(data => {
        pageData = data;
        console.log(pageData);
        if(data.acctId!=""){
            window.location.href = "http://localhost:4002/project-1/home.html";
            console.log("End of if statement")
        } else {
            document.getElementById("invalidBox").innerText = "Invlaid email or password"
        }

    })
    } else{
        alert("Bugger off");
    }
 }
 
 function resetPW(){
	 event.preventDefault();
     var email = document.getElementById("reset-email").value;
     var sq = document.getElementById("questionAnswer").value;
     var pass = document.getElementById("reset-password").value;
     console.log("Email: " + email);
     console.log("sq: " + sq);
     fetch("http://localhost:4002/project-1/resetpw", {
         method: 'POST',
         body: JSON.stringify({
             email: email,
             sq: sq,
             passWord: pass
         })
     })
 }