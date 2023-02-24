$('input[type=file]').change(function(){
    var filename = $(this).val().split('\\').pop();
    var idname = $(this).attr('id');
    console.log(filename);
    console.log(idname);
    $('label.'+idname).html(filename);
});