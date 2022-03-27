package week02.practice.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            String uri = fullHttpRequest.uri();

            if (uri.contains("/test")) {
                handerTest(fullHttpRequest, ctx,"hello netty");
            }else{
                handerTest(fullHttpRequest, ctx,"hello others");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handerTest(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx,String message) {
        FullHttpResponse response = null;
        try{
            String value = message;

            response = new DefaultFullHttpResponse(HTTP_1_1, OK,Unpooled.wrappedBuffer(value.getBytes()));
            response.headers().set("Content-Type","application/json");
            response.headers().setInt("Content-Length",response.content().readableBytes());
        }catch (Exception ex){
            System.out.println("处理出错"+ex.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        }finally {
            if(fullHttpRequest!=null){
                if(!HttpUtil.isKeepAlive(fullHttpRequest)){
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                }else {
                    response.headers().set(CONNECTION,KEEP_ALIVE);
                    ctx.write(response);
                }
            }
        }
    }
}
