function myLike(id,newsId){
    // var params = {newsId:newsId};
    // var url = '/like';
    // $.get(url,params)
    var request = getXMLHttpRequest();
    request.open("GET", "/like?newsId=" + newsId, true);
    request.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var myObj = request.responseText;
            var myJSON =  JSON.parse(myObj);
            if(myJSON.code == 0){
               id.innerHTML= "<i class=\"vote-arrow\"></i><span class=\"count\"> " + myJSON.msg +  "</span>";
            }
        }
    };
    request.send();
}
function myDislike(id,newsId){
    // var params = {newsId:newsId};
    // var url = '/dislike';
    // $.get(url,params)
    var request = getXMLHttpRequest();
    request.open("GET", "/dislike?newsId=" + newsId, true);
    request.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var myObj = request.responseText;
            var myJSON =  JSON.parse(myObj);
            if(myJSON.code == 0){
                id.previousElementSibling.innerHTML = "<i class=\"vote-arrow\"></i><span class=\"count\"> " + myJSON.msg +  "</span>";
            }
        }
    };
    request.send();
}

function getXMLHttpRequest(){
    var xhttp;
    if (window.XMLHttpRequest) {
        xhttp = new XMLHttpRequest();
    } else {
        // code for IE6, IE5
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xhttp;
}