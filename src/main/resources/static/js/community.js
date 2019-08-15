/*
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#questioncomment_content").val();
   questionOrComment(questionId,content,1);
    //console.log(questionId);
    //console.log(content);


}
//封装方法
function questionOrComment(targetId,content,type) {
    if(!content){
        alert("不能回复空的内容");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":targetId,
            "commentContent":content,
            "type":type
        }),
        success:function (response) {
            if (response.code == 200) {
                window.location.reload();
            }else {
                if(response.code == 2003){
                    var isAccept = confirm(response.message);
                    if(isAccept){
                        window.open("https://github.com/login/oauth/authorize?client_id=e3c43418b6205f976f92&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);

                    }
                }else {
                    alert(response.message);
                }
            }
            console.log(response)
        },
        dataType:"json"
    });
}
/*
 * 展开二级评论
 */
function replycomment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //判断当前class是否有in的属性，即是否是展开状态
    if (comments.hasClass("in")){
        //折叠
        e.classList.remove("active");
        comments.removeClass("in");
    }else {
        var subCommentContainer = $("#comment-" + id);
        if(subCommentContainer.children().length != 1){
            //展开
            e.classList.add("active");
            comments.addClass("in");
        }else {
            $.getJSON( "/comment/" + id, function( data ) {

                $.each(data.data.reverse(), function (index,comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.commentContent
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);

                });
                e.classList.add("active");
                comments.addClass("in");

            });
        }



    }

}
/*
 * 提交二级评论
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    questionOrComment(commentId,content,2);
}
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}
function showTag() {
if($("#select_tag").css('display') == 'none'){
    $("#select_tag").show();
}else {
    $("#select_tag").hide();

}
}