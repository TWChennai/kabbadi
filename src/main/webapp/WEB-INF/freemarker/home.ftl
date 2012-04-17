<!DOCTYPE html>
<html>
<head>
    <title>kabaddi</title>
    <link rel="javascript" href="static/js/bootstrap.tab.js">
    <link href="static/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
        body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
        }
    </style>
    <link href="static/css/bootstrap-responsive.css" rel="stylesheet">
</head>


<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">

            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="home.html"> </a>

        </div>
    </div>
</div>
<ul class="nav nav-tabs" id="tab" align="right">
    <li class="active"><a data-toggle="tab" href="home.html">Admin</a></li>
    <li class=""><a data-toggle="tab" href="home.html">Finance</a></li>
    <li class=""><a data-toggle="tab" href="home.html">IS</a></li>

</ul>


<div class="container" position="relative">
    <section id="gridCustomization">
        <div class="page-header" align="right">
            <button href="#" class="btn btn-inverse">Add New</button>
            <button href="#" class="btn btn-inverse">Generate Report</button>
        </div>

        <table class="table table-bordered table-striped">
            <thead>
            <tr>

                <th></th>
                <th>First</th>
                <th>First</th>
                <th>First</th>
                <th>First</th>
                <th>First</th>
                <th>First</th>
                <th>First</th>
                <th>First</th>
            </tr>
            </thead>
            <tbody style="word-wrap:break-word, break-word: hyphenate">
            <#list invoices as invoice>

                <tr>
                    <td style="width: 130px"><span class="label" style="background-color:white"><a
                            href="#">EDIT</a></span> <span
                            class="label" style="background-color:white"> <a
                            href="#">VIEW DETAILS</a></span></td>
                    <td style="width: 130px">${invoice}</td>
                    <td>Provides</td>
                    <td>Provides</td>
                    <td>Provides</td>
                    <td>Provides</td>
                    <td>Provides</td>
                    <td>Provides</td>
                    <td>Provides</td>
                </tr>
            </#list>


            </tbody>
        </table>
        <!--<div class="page-header">-->
        <!--<#if user??>-->
        <!--<h1>Hahguujguygullo ${user.name}</h1>-->
        <!--<#else>-->
        <!--<#if username??>-->
        <!--<h1>Sorry, didn't find a user called "${username}"</h1>-->
        <!--</#if>-->

        <!--<p><a href="?username=bill">Try me!!!!!</a></p>-->


        <!--</#if>-->
        <!--</div>-->
    </section>

</div>

<script src="http://platform.twitter.com/widgets.js" type="text/javascript"></script>
<script src="assets/js/jquery.js"></script>
<script src="assets/js/google-code-prettify/prettify.js"></script>
<script src="assets/js/bootstrap-transition.js"/>
<script src="assets/js/bootstrap-alert.js"/>
<script src="assets/js/bootstrap-modal.js"/>
<script src="assets/js/bootstrap-dropdown.js"/>
<script src="assets/js/bootstrap-scrollspy.js"/>
<script src="assets/js/bootstrap-tab.js"/>
<script src="assets/js/bootstrap-tooltip.js"/>
<script src="assets/js/bootstrap-popover.js"/>
<script src="assets/js/bootstrap-button.js"/>
<script src="assets/js/bootstrap-collapse.js"/>
<script src="assets/js/bootstrap-carousel.js"/>
<script src="assets/js/bootstrap-typeahead.js"/>
<script src="assets/js/application.js"/>

</body>
</html>
