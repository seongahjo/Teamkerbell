<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Teamkerbell</title>

  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="css/wait1.css">
  <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">

  <link rel="stylesheet" href="css/wait2.css">
  <style>
    .fa-user{
      color: #ddd;
      border: solid 3px;
      padding: 10%;
      border-radius: 40% 40% 40% 40%;
    }
    .fb-logo{
      color:#3B5998;
    }
    .gg-logo{
      color:#dd4b39;
    }
   
  </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="../dashboard/${user.id}" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>T</b>eam</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>Teamker</b>Bell</span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
               
                <ul class="nav navbar-nav">
				
				
                </ul>
            </div>
        </nav>
    </header>
    <div class="row" style="background-color:#ffffff;">
    
    <img class="fairy" src="img/fairy.png" style="width:280px;height:280px;margin:110px;margin-right:50px;margin-left:190px;">
    <div role="document"style="width:40%;height:500px;float:right; margin-top:50px;margin-bottom:50px;margin-right:70px;padding-right:50px;">
     <h1>Sign in to start your session</h1>
      <form action="loginprocess" method="post" id="loginForm">
    	<div class="form-group has-feedback"style="margin-top:50px;margin-bottom:20px">
            <input style="height:45px" type="ID" name="userId" class="form-control log_input" placeholder="ID">
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
          </div>
          
          <div class="form-group has-feedback"style="margin-top:30px;margin-bottom:30px">
            <input style="height:45px" type="password" name="pw" class="form-control log_input" placeholder="Password">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="row">
            <div class="col-xs-8">
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox"> Remember Me
                </label>
              </div>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">
              <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
            </div>
            <!-- /.col -->
          </div>
          </form>
          <div class="social-auth-links text-center" data-toggle="modal" data-target="#signupModal">
          <a  class="btn btn-block btn-social btn-twitter btn-flat"><i class="fa fa-sign-in"></i> Sign up</a>
          <div class="social-auth-links text-center" data-toggle="modal" data-target="#facebookModal">
          <a  class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using
            Facebook</a>
     	</div>
    	 <div class="social-auth-links text-center"  data-toggle="modal" data-target="#googleModal">
          <a class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign up using
            Google+</a>
       	 </div>
    </div>
    
    </div>

</div>

<div class="modal fade" id="signupModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">


    <div class="register-box">

      <div class="register-box-body">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <div class="login-logo" style="text-align:center">
          <i class="fa fa-user fa-1x"></i>
        </div>
        <p class="login-box-msg">Register a new membership</p>

        <form action="register" method="post" id="registerForm" enctype="multipart/form-data">
          <div class="form-group has-feedback">
            <input type="text" class="form-control" name="name" placeholder="Full name">
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="id" class="form-control" name="id" placeholder="ID">
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" id="pw" name="pw" placeholder="Password">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" id="pw2" placeholder="Retype password">
            <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
          </div>
          <div class="form-group">
            <label for="exampleInputFile">Select image</label>
            <input type="file" name="file" onchange="previewImage(this,'preimage')">
          </div>
          <div id='preimage' style="width:100px;height:100px"></div>

          <div class="row">
            <div class="col-xs-8">
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox" id="check" > I agree to the <a href="#">terms</a>
                </label>
              </div>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">
              <button type="button" class="btn btn-primary btn-block btn-flat" onclick="register()">Register</button>
            </div>
            <!-- /.col -->
          </div>
        </form>
        <div class="social-auth-links text-center">

        </div>
      </div>
      <!-- /.form-box -->
    </div>
    <!-- /.register-box -->
  </div>
</div>



<div class="modal fade" id="facebookModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="login-box">

      <div class="login-logo" style="text-align:center">
        <i class="fa fa-facebook fa-2x fb-logo"></i>
      </div>

      <div class="login-box-body">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>


        <p class="login-box-msg">Sign in Using Facebook</p>
        <form action="#" method="post">
          <div class="form-group has-feedback">
            <input type="email" class="form-control" placeholder="Email">
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" placeholder="Password">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="row">
            <div class="col-xs-8">
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox"> Remember Me
                </label>
              </div>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">
              <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
            </div>
            <!-- /.col -->
          </div>
        </form>
        <div class="social-auth-links text-center">
          <a  class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using
            Facebook</a>
        </div>
        <!-- /.social-auth-links -->
      </div>
    </div>
  </div>
</div>




<div class="modal fade" id="googleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="login-box">

      <div class="login-logo" style="text-align:center">
        <i class="fa fa-google-plus fa-2x gg-logo"></i>
      </div>

      <div class="login-box-body">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>


        <p class="login-box-msg">Sign in Using Google</p>
        <form action="#" method="post">
          <div class="form-group has-feedback">
            <input type="email" class="form-control" placeholder="Email">
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" placeholder="Password">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="row">
            <div class="col-xs-8">
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox"> Remember Me
                </label>
              </div>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">
              <button type="submit" class="btn btn-danger btn-block btn-flat">Sign In</button>
            </div>
            <!-- /.col -->
          </div>
        </form>
        <div class="social-auth-links text-center">
          <a class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign up using
            Google+</a>
        </div>
        <!-- /.social-auth-links -->
      </div>
    </div>
  </div>
</div>
<script src="js/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="js/app.min.js"></script>
<script src="js/date.js"></script>
<script src="js/previewimage.js"></script>
<script>
  function register(){
   if(($("#pw").val())==($("#pw2").val())){
     if($("#check").prop('checked')==true){
       var form = $("#registerForm")[0];
       var formData = new FormData(form);
       $.ajax({
         url: "../register",
         type: "POST",
         data: formData,
         processData: false,
         contentType: false,
         success: function () {
           location.reload();
         }
       });
     }
   }
  }

</script>
</body>
</html>