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


// function pageSetup(data) {
//     console.log("Page data: "+pageData);
//     console.log("First name: ")
//         console.log(data.firstName);
//         document.getElementById("nameBox").innerText = data.firstName;
// }

function pullUserData(){
     event.preventDefault();
     console.log("Pull User Details");
     fetch("http://localhost:4002/project-1/home")
     .then(res => {
         return res.json();
     })
     .then(data => {
         if(data.acctId == ""){
            window.location.href = "http://localhost:4002/project-1/index.html";
         } else{
         document.getElementById("nameBox").innerHTML = "Welcome back, "+data.firstName + " " + data.lastName;
         }
     })
     fetch("http://localhost:4002/project-1/accounts")
     .then(res => {
         return res.json();
     })
     .then(data => {
         console.log(data);
    	 var acct = "";
         for(i = 0; i < data.length; i++){
            acct = acct + "<div class='account' onclick='callVATM("+ data[i].acctId +")'> Account ID: " + data[i].acctId + " Current Balance: " + data[i].balance +  " Account Type: " + data[i].acct_type + "</div><br>" 
            }
            document.getElementById("acct-box").innerHTML = acct;
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
     console.log(dob);
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
     if($("#resetpw").valid()){
     fetch("http://localhost:4002/project-1/resetpw", {
         method: 'POST',
         body: JSON.stringify({
             email: email,
             sq: sq,
             passWord: pass
         })
     })
    }
 }

 function logout(){
    //  console.log("Logout")
    fetch("http://localhost:4002/project-1/logout")
    .then(res => window.location.href = "http://localhost:4002/project-1/index.html");
 }
 
 function GetTodayDate() {
    var tdate = new Date();
    var dd = tdate.getDate(); //yields day
    var MM = tdate.getMonth(); //yields month
    var yyyy = tdate.getFullYear(); //yields year
    var currentDate= yyyy + "-" + "09" + "-" + dd;
    return currentDate;
 }
//1991-10-28
 function createAcct(){
	 event.preventDefault();
     var acctType = $("input[name='acct-type']:checked"). val();
     var startBal = $("#starting-bal").val();
     var interest = 0;
     if(acctType == "savings"){
        interest = 5;
     }
     var date = GetTodayDate();

    console.log(date);
     console.log(startBal);
	fetch("http://localhost:4002/project-1/create", {
        method: 'POST',
        body: JSON.stringify({
            acct_type: acctType,
            balance: startBal,
            interest: interest,
            openDate: date
        })
    })
    .then(res => {
        return res.json()
    })
    .then(data => {
        if(data.acctId != "") {
            $('#create-account').modal('hide');
            location.reload();
        }
    })
 }

function callVATM(e){
    document.getElementById("acctId"). value = e;
    $('#make-transaction').modal('show');
}

 function makeTransaction(){
     event.preventDefault();
     console.log("transction function");
     var acctId = document.getElementById("acctId").value;
     var transType = $("input[name='trans-type']:checked"). val();
     var transAmount = document.getElementById("trans-amount").value;
     if(transType == "withdrawal"){
         transAmount = transAmount - (transAmount * 2);
     }
     if (transAmount < 0){
    	 transAmount = transAmount * -1
     }
     console.log(acctId);
     console.log(transType);
     console.log(transAmount);
      fetch("http://localhost:4002/project-1/transaction", {
          method: 'POST',
          body: JSON.stringify({
              amount: transAmount,
              acctId: acctId
          })
      })
      .then(location.reload())
 }