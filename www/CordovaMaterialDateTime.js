var exec = require('cordova/exec');


/*let Datepickerargs = {
    title:'',
    color:'',
    oktext:'', // OK button text
    canceltext:'', // Cancel button text
    mindate:'', // MUST BE IN dd-MM-yyyy format
    maxdate:'', // MUST BE IN dd-MM-yyyy format
    highlitedDates:['',''], // DATES MUST BE IN dd-MM-yyyy format
    showyearpickerbeforemonth:false,
    vibrateontouch:true,
    autodismiss:true, // DIsmiss when choosing a date automatically
}*/

/*
let Timepickerargs = {
    hours:'',
    minutes:'',
    seconds:'',
    title:'',
    color:'',
    oktext:'', // OK button text
    canceltext:'', // Cancel button text
    mintime:'', // ARRAY of HOURS, MINUTES, SECONDS [23,12,44]
    maxtime:'', // ARRAY of HOURS, MINUTES, SECONDS [23,12,44]
    enableSeconds:false,
    enableMinutes:true,
    vibrateontouch:true,
}*/

exports.DatePicker = function (arg0) {
    return new Promise((res,rej)=>{
        let arg =[];
        arg.push(arg0.title || '');
        arg.push(arg0.color || '');
        arg.push(arg0.oktext || '');
        arg.push(arg0.canceltext || '');
        arg.push(arg0.mindate || '');
        arg.push(arg0.maxdate || '');
        arg.push(arg0.highlitedDates || []);
        arg.push(arg0.showyearpickerbeforemonth || false);
        arg.push(arg0.vibrateontouch || true);
        arg.push(arg0.autodismiss || true);
        exec((success)=>{res(success)}, (error)=>{rej(error)}, 'CordovaMaterialDateTime', 'datepicker', arg);
    })
   
};

exports.TimePicker = function (arg0) {
    return new Promise((res,rej)=>{
        let arg =[];
        arg.push(arg0.hours || '');
        arg.push(arg0.minutes || '');
        arg.push(arg0.seconds || '');
        arg.push(arg0.title || '');
        arg.push(arg0.color || '');
        arg.push(arg0.oktext || '');
        arg.push(arg0.canceltext || '');
        arg.push(arg0.mintime || []);
        arg.push(arg0.maxtime || []);
        arg.push(arg0.enableSeconds || false);
        arg.push(arg0.enableMinutes || false);
        arg.push(arg0.vibrateontouch || false);
        exec((success)=>{res(success)}, (error)=>{rej(error)}, 'CordovaMaterialDateTime', 'timepicker', arg);
    })

};