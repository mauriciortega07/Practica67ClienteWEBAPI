$(document).ready(function(){

    $("#errorModal").hide();
    $("#successModal").hide();

    $("#guardarFormulario").click(function(event){
    event.preventDefault();

       $("#errorModal").hide();
       $("#successModal").hide();

       //obtenemos campos
       var FormUsuarioId = $("#FormUsuarioId").val();
       var FormUsuarioNombre = $("#FormUsuarioNombre").val();
       var FormUsuarioEdad = $("#FormUsuarioEdad").val();

       var FormTelefonoNumero = $("#FormTelefonoNumero").val();
       var FormTelefonoLada = $("#FormTelefonoLada").val();
       var FormTelefonoTipo = $("#FormTelefonoTipo").val();


        $.ajax({
            type:"POST",
            url:"/guardar-usuario",
            data:{"FormUsuarioId": FormUsuarioId,
              "FormUsuarioNombre":FormUsuarioNombre,
              "FormUsuarioEdad": FormUsuarioEdad,

              "FormTelefonoNumero":FormTelefonoNumero,
              "FormTelefonoLada": FormTelefonoLada,
              "FormTelefonoTipo": FormTelefonoTipo
              },

            success: function(response){
            $("#successMessage").text(response.message);

            $("#successModal").show();
            //console.log("Usuario Guardado correctamente.");
            },

            error: function(XMLHttpRequest, textStatus ,errorThrown) {

               if(XMLHttpRequest.responseJSON != null) {
                    if(XMLHttpRequest.responseJSON.error){
                        $("#errorMessage").text(XMLHttpRequest.responseJSON.error);
                    } else if (XMLHttpRequest.responseJSON.message){
                        $("#errorMessage").text(XMLHttpRequest.responseJSON.message)
                    } else {
                        $("#errorMessage").text("Ocurrio un error al GUARDAR el usuario, consulte al Administrador.");
                    }

               } else {
                    $("#errorMessage").text("Ocurrio un error al GUARDAR el usuario, consulte al Administrador.");
               }

                    $("#errorModal").show();
                    //console.log(XMLHttpRequest.responseJSON.error);

            }
        });
    });
});