function myLike(newsId){
    var params = {newsId:newsId};
    var url = '/like';
    $.get(url,params)
}
function myDislike(newsId){
    var params = {newsId:newsId};
    var url = '/dislike';
    $.get(url,params)
}