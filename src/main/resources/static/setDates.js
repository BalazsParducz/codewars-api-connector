
let now = new Date();
let twoWeeksEarlier = now.getTime()-14*24*60*60*1000;
let twoWeeksAgo = new Date(twoWeeksEarlier);

function setDate() {
    document.getElementById("from").value =twoWeeksAgo.getFullYear() +'-'+(twoWeeksAgo.getMonth()+1).toString().padStart(2, "0")+'-'+twoWeeksAgo.getDate().toString().padStart(2, "0");
    document.getElementById("to").value= now.getFullYear() +'-'+(now.getMonth()+1).toString().padStart(2, "0")+'-'+now.getDate().toString().padStart(2, "0");
}

document.getElementById("setDate").onclick = setDate;

