<%@page contentType="text/html"
pageEncoding="UTF-8"%>

<div class="header">

<div class="judulheader">

<img

src="<%=request.getContextPath()%>/images/logo_unpam.png"

class="logounpam"

>

<div>

<h1>

Informasi Nilai Mahasiswa

</h1>

<h2>

UNIVERSITAS PAMULANG

</h2>

<p>

Jl. Surya Kencana No.1 Pamulang

</p>

</div>

</div>

</div>

<nav>

<ul>

<li>

<a href="index.jsp">

<img

src="<%=request.getContextPath()%>/images/logo_unpam.png"

class="iconmenu"

>

Home

</a>

</li>

<li>

<a href="#">

<img

src="<%=request.getContextPath()%>/images/logo_unpam.png"

class="iconmenu"

>

Master Data

</a>

<ul>

<li>

<a href="dataMahasiswa.jsp">

<img

src="<%=request.getContextPath()%>/images/logo_unpam.png"

class="iconsubmenu"

>

Data Mahasiswa

</a>

</li>

<li>

<a href="dataMatkul.jsp">

<img

src="<%=request.getContextPath()%>/images/logo_unpam.png"

class="iconsubmenu"

>

Mata Kuliah

</a>

</li>

</ul>

</li>

<li>

<a href="inputNilai.jsp">

<img

src="<%=request.getContextPath()%>/images/logo_unpam.png"

class="iconmenu"

>

Transaksi

</a>

</li>

<li>

<a href="laporanNilai.jsp">

<img

src="<%=request.getContextPath()%>/images/logo_unpam.png"

class="iconmenu"

>

Laporan

</a>

</li>

<li>

<a href="login.jsp">

<img

src="<%=request.getContextPath()%>/images/logo_unpam.png"

class="iconmenu"

>

Login

</a>

</li>

</ul>

</nav>