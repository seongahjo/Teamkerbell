<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en"> <!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Teamkerbell</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="../css/wait1.css">
    <link rel="stylesheet" href="../css/jquery-jvectormap-1.2.2.css">
    <link rel="stylesheet" href="../css/wait2.css">


    <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> -->
    <!--  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/../js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script> -->
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <link rel="stylesheet" type="text/css" href="../css/cal_demo.css"/>
    <link rel="stylesheet" type="text/css" href="../css/top_calendar.css"/>
    <link rel="stylesheet" type="text/css" href="../css/custom_2.css"/>
    <!-- daterange picker -->
    <link rel="stylesheet" href="../css/daterangepicker-bs3.css">
    <link rel="stylesheet" href="../css/bootstrap-timepicker.min.css">
    <link rel="stylesheet" href="../css/dataTables.bootstrap.css">
    <!-- daterange picker -->
    <link rel="stylesheet" href="../css/daterangepicker-bs3.css">

    <!-- iCheck for checkboxes and radio inputs -->

    <link href="../css/check_style.css" rel="stylesheet">

    <!-- Select2 -->
    <link rel="stylesheet" href="../css/select2.min.css">
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
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">

                    <!-- Notifications Menu -->
                    <li data-toggle="modal" data-target="#photoModal">
            	<a data-toggle="tooltip" title=""  data-placement="bottom" data-original-title="Images Gallery">
              	<i class="fa fa-picture-o"></i>
            	</a>
            	</li>
            	
            	
            	<li data-toggle="modal" data-target="#todoList" >
            	<a data-toggle="tooltip" title=""  data-placement="bottom" data-original-title="to do List">
              	<i class="fa fa-edit"></i>
            	</a>
            	</li>

            	 <li  onclick="location.href='../filemanager/${project.projectidx}';">
            	<a data-toggle="tooltip" title="" data-placement="bottom"data-original-title="File Manager">
              	<i class="fa fa-file-text-o"></i>
            	</a>
            	
            	</li>
            	
            	 <li onclick="location.href=' ';">
            	<a data-toggle="tooltip" title="" data-placement="bottom"data-original-title="Goback to Project">
              	<i class="fa fa-undo"></i>
            	</a>
            	</li>
                    <li class="dropdown notifications-menu">
                    
                        <!-- Menu toggle button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning">${alarm.size()}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have ${alarm.size()} notifications</li>
                            <li>
                                <ul class="menu" style="max-height:400px;overflow-y:auto">
                                    <c:forEach var="list" items="${alarm}">
                                        <li id="alarm-${list.alarmidx}">
                                            <a href="#">
                                                <i class="fa fa-users text-aqua"></i><strong>${list.actor.id}</strong>
                                                has invited you
                                                to <strong>${list.project.name}</strong>
                                                <div style="float:right;">
                                                    <button type="button" class="btn btn-primary btn-xs"
                                                            onclick="accept('${list.alarmidx}')">Ok
                                                    </button>
                                                    <button type="button" class="btn btn-default btn-xs"
                                                            onclick="decline('${list.alarmidx}')">Cancel
                                                    </button>
                                                </div>
                                            </a>
                                        </li>
                                    </c:forEach>

                                </ul>
                            </li>
                            <li class="footer"> 
                        </ul>
                    </li>

                    <!-- User Account Menu -->
                    <li class="dropdown user user-menu">
                        <!-- Menu Toggle Button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <!-- The user image in the navbar-->
                            <img src="../${user.img}" class="user-image" alt=""style="width:25px;height:25px">
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                            <span class="hidden-xs">${user.id}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                            <li class="user-header">
                                <img src="../${user.img}" class="img-circle" alt="User Image"style="width:90px;height:90px">

                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-6 text-center" style="border-right:1px solid;">
                                        <a href="../projectmanager"><i class="fa fa-pencil-square-o"></i> Project Edit</a>
                                    </div>
                                    <div class="col-xs-6 text-center">
                                        <a href="../userInfo/${user.id}"><i class="fa fa-info-circle"></i> MyInfo Edit</a>
                                    </div>

                                </div>
                                <!-- /.row -->
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                                </div>
                                <div class="pull-right">
                                    <a href="../" class="btn btn-default btn-flat">Sign out</a>
                                </div>
                            </li>
                        </ul>
                    </li>

                </ul>
            </div>
        </nav>
    </header>
    <aside class="main-sidebar">
        <section class="sidebar">
            <div class="user-panel">
                <div class="pull-left image">
                    <img src="../${user.img}" class="img-circle" alt="User Image" style="width:100px;height:100px">
                </div>
                <div class="pull-left info dash-user">
                    <p>${user.id}</p>
                    <!-- Status -->
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu">
                <li class="header"> Main Menu</li>
                <!-- Optionally, you can add icons to the links -->

                <li class="treeview">
                    <a href="#"><i class="fa fa-user"></i><span> My Profile</span></a>
                    <ul class="treeview-menu">
                        <li class="active"><a href=../userInfo/${user.id}><i class="fa fa-key"></i> Change user Info</a></li>
                   
                    </ul>
                </li>

                <li class="treeview">
                    <a href="#"><i class="fa fa-users"></i><span> Project rooms</span></a>
                    <ul class="treeview-menu">
                        <li>
                            <c:forEach var="list" items="${projects}">
                                <a href="../chat/${list.projectidx}" class="side-nav-button">name
                                    : ${list.name}</a>
                            </c:forEach>
                            <a href="../projectmanager"> <i class="fa fa-cogs"></i><span>Edit</span></a>
                        </li>

                    </ul>
                </li>
                
            </ul>
            <!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">       
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <!--calendar-->
                <div class="col-md-6">

                    <section class="main">
                        <div class="custom-calendar-wrap">
                            <div id="custom-inner" class="custom-inner">
                                <div class="custom-header clearfix">
                                    <nav>
                                        <span id="custom-prev" class="custom-prev"></span>
                                        <span id="custom-next" class="custom-next"></span>
                                    </nav>
                                    <h2 id="custom-month" class="custom-month"></h2>
                                    <h3 id="custom-year" class="custom-year"></h3>
                                </div>
                                <div id="calendar" class="fc-calendar-container"></div>
                            </div>
                        </div>
                    </section>
                </div>

                <section class="col-md-6 ">
                    <div class="box" style="height:470px">
                        <div class="box-header">
                            <h3 class="box-title">Files</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="file" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Uploader</th>
                                    <th>Date</th>
                                </tr>
                                </thead>
                                <tbody id="tablebody">

                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>Name</th>
                                    <th>Uploader</th>
                                    <th>Date</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>


                </section>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header add-header">
                            <h3 class="box-title"> Add Meeting Schedule <i class="fa fa-calendar-plus-o"
                                                                           data-toggle="modal"
                                                                           data-target="#myModal3"></i></h3>

                            <div class="box-tools">
                                <div class="input-group input-group-sm" style="width: 150px;">
                                    <input type="text" name="table_search" class="form-control pull-right"
                                           placeholder="Search">

                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-default"><i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body table-responsive no-padding" style=" height: 270px; overflow: auto">
                            <table class="table table-hover">
                                <tr>
                                    <th>Date</th>
                                    <th>Status</th>
                                    <th>To Do</th>
                                    <th>Place</th>
                                    <th>Time</th>
                                    <th>Participation</th>
                                </tr>
                                <c:forEach var="list" items="${schedules}">
                                    <tr>
                                        <td>${list.startdate} - ${list.enddate}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${list.state=='0'}">
                                                    <a class="btn btn-primary btn-xs" data-toggle="modal"
                                                       data-target="#Rgmodal"
                                                       onclick="toregister('${list.scheduleidx}','${list.startdate}','${list.enddate}')">Register</a>
                                                </c:when>
                                                <c:when test="${list.state=='1'}">
                                                    <a class="btn btn-danger btn-xs" data-toggle="modal"
                                                       data-target="#firmupModal"
                                                       onclick="load_firm('${list.scheduleidx}')">Firm up</a>
                                                </c:when>
                                                <c:when test="${list.state=='2'}">
                                                    <a class="btn btn-warning btn-xs" data-toggle="modal"
                                                       data-target="#finishMadal"
                                                       onclick="load_finish('${list.scheduleidx}')">Finish</a>
                                                </c:when>
                                                <c:when test="${list.state=='3'}">
                                                    <a class="btn btn-default btn-xs">Completion</a>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>${list.content}</td>
                                        <td>${list.place}</td>
                                        <td>${list.time}</td>
                                        <td>
                                            <c:forEach var="ap" items="${list.appointments}">
                                                <c:choose>
                                                    <c:when test="${ap.state=='1' }">
                                                        ${ap.date} : ${ap.user.name}
                                                        <br>
                                                    </c:when>
                                                    <c:when test="${ap.state=='3' }">
                                                        <strong>${ap.date} : ${ap.user.name}</strong>
                                                        <br>
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>
            <!--2row end-->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer">
        <!-- To the right -->
        <div class="pull-right hidden-xs">
            Teamkerbell
        </div>
    </footer>

    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<div class="control-sidebar-bg"></div>
<input id="reloadValue" type="hidden" name="reloadValue" value=""/>

<!--register modal -->
<div class="modal fade" id="Rgmodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">Register Schedule</h4>
            </div>
            <form id="registerform">
                <div class="modal-body">

                    <div class="form-group">

                        <label>Sign the Date range:</label>

                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" class="form-control pull-right" id="reservation">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="register()">Confirm</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- add schedule -->
<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">Add Schedule</h4>
            </div>
            <form id="scheduleform">
                <div class="modal-body">

                    <div class="form-group">
                        <div class="pull-right">
                            <label>Sign the Date range:</label>
                            <div class="input-group">
                                <button type="button" name="a" class="btn btn-default pull-right" id="daterange-btn">
                                    <i class="fa fa-calendar"></i>
                                    <span>Date range picker</span>
                                    <i class="fa fa-caret-down"></i>
                                </button>
                            </div>
                        </div>
                        <br>
                        <br>
                        <br>
                        <div class="form-group has-success">
                            <label class="control-label" for="inputSuccess"><i class="fa fa-check"></i> List what you
                                have to do</label>
                            <input type="text" class="form-control" id="content" name="content" placeholder="To do ...">
                        </div>
                        <div class="form-group has-success">
                            <label class="control-label" for="inputSuccess"><i class="fa fa-map-marker"></i> Write where
                                you want to meet</label>
                            <input type="text" class="form-control" id="place" name="place" placeholder="place ...">
                        </div>

                        <!-- /.input group -->

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onClick="makeSchedule()">Make</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- firmup -->
<div class="modal fade" id="firmupModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">Firm up the details</h4>
            </div>
            <form>
                <div class="modal-body">

                    <div class="form-group">
                        <strong>Date check</strong>
                        <br>
                        <div id="date-check" style="">
                        </div>

                        <!-- time Picker -->
                        <div class="bootstrap-timepicker">
                            <div class="form-group">
                                <label>Time :</label>

                                <div class="input-group">
                                    <input type="text" class="form-control timepicker">

                                    <div class="input-group-addon">
                                        <i class="fa fa-clock-o"></i>
                                    </div>
                                </div>
                                <!-- /.input group -->
                            </div>
                            <!-- /.form group -->
                        </div>            
                        <div class="form-group has-success">
                            <label class="control-label" for="inputSuccess"><i class="fa fa-map-marker"></i> Write where
                                you want to meet</label>
                            <input type="text" class="form-control" id="make_place" placeholder="place ...">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onClick="make_firm()">Update</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="finishMadal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">Finish the details</h4>
            </div>
            <form id="finishForm">
                <div class="modal-body">

                    <div class="form-group">
                        <div class="form-group has-success">
                            <label class="control-label" for="inputSuccess"><i class="fa fa-users"></i> Write the Real
                                Participation</label>
                            <select class="form-control select2" id="select" name="names" multiple="multiple"
                                    data-placeholder="Select a person" style="width: 100%;">
                                <c:forEach var="list" items="${users}">
                                    <option value="${list.id}">${list.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onClick="finish()">Update</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- jQuery 2.1.4 -->
<script src="../js/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="../js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="../js/app.min.js"></script>
<script src="../js/modernizr.custom.63321.js"></script>
<script src="../js/prettydate.min.js"></script>
<script src="../js/jquery.calendario.js"></script>
<script src="../js/modernizr.custom.63321.js"></script>
<!-- date -->
<script src="../js/date.js"></script>
<!--Table-->
<script src="../js/jquery.dataTables.min.js"></script>
<script src="../js/dataTables.bootstrap.min.js"></script>
<!-- date-range-picker -->
<script src="../js/moment.min.js"></script>
<script src="../js/daterangepicker.js"></script>
<!-- tag-->
<script src="../js/addtags.js"></script>
<!-- bootstrap time picker -->
<script src="../js/bootstrap-timepicker.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="../js/jquery-checkbox.js" type="text/javascript"></script>
<!-- InputMask -->
<script src="../js/jquery.inputmask.js"></script>
<!-- Select -->
<script src="../js/select2.full.min.js"></script>
<script>
    var Events;
    var param = "projectIdx=" +${project.projectidx};


    var scheduleIdx; //register scheduleIdx
    var registerStartDate; // register start
    var registerEndDate; // register end
    var scheduleStart; // reservation start
    var scheduleEnd; // reservation end
    var table; // table
    var clickDate = "2016-1-6"; // click date

    var make_checkbox; //make meeting checkbox
    var make_time; // make meeting time

    $(function () {
        //Initialize Select Elements
        $(".select2").select2();

        table = $('#file').DataTable({
            "ajax": {
                "url": "../selectDate",
                "type": "GET",
                "data": function (d) {
                    d.projectIdx=${project.projectidx};
                    d.date = clickDate;
                },
                "columns": [
                    {"data": "Name"},
                    {"data": "Uploader"},
                    {"data": "Date"}
                ]
            },
            "reload": true,
            "paging": true,
            "lengthChange": false,
            "searching": false,
            "ordering": true,
            "info": true,
            "autoWidth": false
        });

        $.ajax({
            url: "../getEvent",
            data: param,
            dataType: 'json',
            async: true,
            processData: false,
            contentType: false,
            type: 'GET',
            success: function (data) {
                Events = data;
                var transEndEventNames = {
                            'WebkitTransition': 'webkitTransitionEnd',
                            'MozTransition': 'transitionend',
                            'OTransition': 'oTransitionEnd',
                            'msTransition': 'MSTransitionEnd',
                            'transition': 'transitionend'
                        },
                        transEndEventName = transEndEventNames[Modernizr.prefixed('transition')],
                        $wrapper = $('#custom-inner'),
                        $calendar = $('#calendar'),
                        cal = $calendar.calendario({
                            onDayClick: function ($el, data, dateProperties) {
                                clickDate = dateProperties.year + "-" + dateProperties.month + "-" + dateProperties.day;
                                table.ajax.reload();
                                if (data.content.length > 0) {
                                    showEvents(data.content, dateProperties);

                                }
                            },
                            caldata: Events,
                            testdata: Events,
                            displayWeekAbbr: true,
                            events: 'click'
                        }),
                        $month = $('#custom-month').html(cal.getMonthName()),
                        $year = $('#custom-year').html(cal.getYear());

                $('#custom-next').on('click', function () {
                    cal.gotoNextMonth(updateMonthYear);
                });

                $('#custom-prev').on('click', function () {
                    cal.gotoPreviousMonth(updateMonthYear);
                });

                function updateMonthYear() {
                    $month.html(cal.getMonthName());
                    $year.html(cal.getYear());
                }

                // just an example..
                function showEvents(contentEl, dateProperties) {
                    hideEvents();
                    var $events = $('<div id="custom-content-reveal" class="custom-content-reveal"><h4>Events for ' + dateProperties.monthname + ' ' + dateProperties.day + ', ' + dateProperties.year + '</h4></div>'),
                            $close = $('<span class="custom-content-close"></span>').on('click', hideEvents);
                    $events.append(contentEl.join(''), $close).insertAfter($wrapper);
                    setTimeout(function () {
                        $events.css('top', '0%');
                    }, 25);
                }

                function hideEvents() {
                    var $events = $('#custom-content-reveal');
                    if ($events.length > 0) {
                        $events.css('top', '100%');
                        Modernizr.csstransitions ? $events.on(transEndEventName, function () {
                            $(this).remove();
                        }) : $events.remove();
                    }
                }
            }
        });
        //Initialize Select Elements
        // $(".select2").select2();

        //reservation
        $('#daterange-btn').daterangepicker({
                    dateFormat: 'yyyy-MM-dd',
                    ranges: {
                        'Today': [moment(), moment()],
                        'This Month': [moment().startOf('month'), moment().endOf('month')]
                    },
                    minDate: new Date()
                },
                function (start, end) {
                    scheduleStart = start;
                    scheduleEnd = end;
                    $('#daterange-btn span').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
                }
        );
        $('#reservation').on('cancel.daterangepicker', function (ev, picker) {
            $(this).val('');
        });

        $(".timepicker").timepicker({
            showMeridian: false,
            showInputs: false
        });

        $('.timepicker').timepicker().on('changeTime.timepicker', function (e) {
            make_time = e.time.value;
            console.log(make_time);
        });


    });
    /*
     외부
     */
    function toregister(_scheduleIdx, startdate, enddate) {
        scheduleIdx = _scheduleIdx;

        $('#reservation').daterangepicker({
            dateFormat: 'yyyy-MM-dd',
            minDate: new Date(startdate),
            maxDate: new Date(enddate),
            startDate: new Date(startdate),
            endDate: new Date(enddate)
        }, function (start, end) {
            registerStartDate = start;
            registerEndDate = end;
        });
    }

    function makeSchedule() {

        var param = "projectIdx=${project.projectidx}&startdate=" + scheduleStart.format('YYYY-MM-DD') + "&enddate=" + scheduleEnd.format('YYYY-MM-DD') + "&content=" + $("#scheduleform #content").val() + "&place=" + $("#scheduleform #place").val() + "&state=0";
        console.log(param);

        $.ajax({
            url: "../makeSchedule",
            data: param,
            dataType: 'text',
            async: true,
            processData: false,
            contentType: false,
            type: 'GET',
            success: function (response) {
                location.reload();
            },
            error: function () {
            }
        });

    }


    $("#Rgmodal").on('hidden.bs.modal', function () {
        $('#reservation').val('');
    });

    function register() {
        console.log(registerStartDate + " " + registerEndDate);
        var param = "userIdx=${user.useridx} &scheduleIdx=" + scheduleIdx + "&startdate=" + registerStartDate.format('YYYY-MM-DD') + "&enddate=" + registerEndDate.format('YYYY-MM-DD') + "&state=0";

        $.ajax({
            url: "../makeRegister",
            data: param,
            dataType: 'text',
            async: true,
            processData: false,
            contentType: false,
            type: 'GET',
            success: function () {
                location.reload();
            },
            error: function () {
            }
        });
    }
    function load_firm(_scheduleIdx) {
        var param = "scheduleIdx=" + _scheduleIdx;
        scheduleIdx = _scheduleIdx;
        $.ajax({
            url: "../loadTime",
            data: param,
            dataType: 'json',
            async: true,
            processData: false,
            contentType: false,
            type: 'GET',
            success: function (data) {
                $("#date-check").html('');
                $.each(data, function (index, val) {
                    $("#date-check").append('<label><input type="checkbox" value="' + val + '" /><span class="help-block" style="float:right">' + val + '</span></label>');
                });
                $('input:checkbox').on('click', function () {
                    make_checkbox = $(this).val();
                    $('input:checkbox').not(this).prop("checked", false);

                });
            }
        });
    }

    function make_firm() {
        var place = $("#make_place").val();
        var param = "scheduleIdx=" + scheduleIdx + "&date=" + make_checkbox + "&time=" + make_time + "&place=" + place;
        $.ajax({
            url: "../makeMeeting",
            data: param,
            dataType: 'text',
            async: true,
            processData: false,
            contentType: false,
            type: 'GET',
            success: function () {
                location.reload();
            }
        });
    }

    function load_finish(_scheduleIdx) {
        scheuldIdx = _scheduleIdx;
    }
    function finish() {
        var param = "scheduleIdx=" + scheuldIdx + "&ids=" + $("#select").children("option:selected").val();
        $.ajax({
            url: "../finishMeeting",
            data: param,
            dataType: 'text',
            async: true,
            processData: false,
            contentType: false,
            type: 'GET',
            success: function () {
                location.reload();
            }
        });
    }


    /* $("#example2").on("click", "a", function () {
     console.log($(this).val());
     });*/
    /*
     'columns': [
     {"data" : "metric_name"},
     {"data" : "metric_type"},
     {"data" : "metric_timestamp"},
     {"data" : "metric_duration"}
     ]
     */

</script>
</body>
</html>