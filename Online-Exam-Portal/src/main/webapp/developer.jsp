<style>
    .person-details {
        display: none;
    }
</style>

<div id="about-section" class="container marketing my-4 pt-5" style="scroll-margin-top: 100px;">

    <h1 class="display-4 text-center mb-4">About Team</h1>
    <hr class="featurette-divider">

    <div class="row justify-content-center mt-4">
        <div class="col-lg-4 d-flex flex-column align-items-center">
            <img class="rounded-circle img-fluid object-fit-cover" src="assets/images/Dev1.jpg" alt="Harshita Image" style="width: 140px; height: 140px; object-fit: cover;">

            <h2>Akshdeep</h2>
            <p class="text-center">Worked on complete design and development of this project.</p>
            <p><a class="btn btn-secondary" href="#details1" role="button" onclick="toggleDetails('details1')">View details »</a></p>

            <div id="details1" class="person-details mt-3 p-3 border rounded">
                <h4>Details about this person:</h4>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Skills:</strong> Java, Servlet, JSP, MySQL, Bootstrap</p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Email:</strong> harshitasingh7004@gmai.com</p>
                        <p><strong>Contribution:</strong> Sole developer and designer of this project</p>
                    </div>
                </div>
            </div>
        </div><!-- /.col-lg-4 -->
    </div>
</div>

<script>
    function toggleDetails(detailsId) {
        var detailsContainer = document.getElementById(detailsId);
        detailsContainer.style.display = (detailsContainer.style.display === 'none') ? 'block' : 'none';
    }
</script>
