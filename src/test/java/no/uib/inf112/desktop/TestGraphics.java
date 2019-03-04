package no.uib.inf112.desktop;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TestGraphics {
    // This is our "test" application
    private static Application application;

    // Before running any tests, initialize the application with the headless backend
    @BeforeClass
    public static void init() {
        // Note that we don't need to implement any of the listener's methods
        application = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {
            }

            @Override
            public void resize(int width, int height) {
            }

            @Override
            public void render() {
            }

            @Override
            public void pause() {
            }

            @Override
            public void resume() {
            }

            @Override
            public void dispose() {
            }
        });

        // Use Mockito to mock the OpenGL methods since we are running headlessly
        Gdx.gl20 = new GL20() {
            @Override
            public void glActiveTexture(int texture) {

            }

            @Override
            public void glBindTexture(int target, int texture) {

            }

            @Override
            public void glBlendFunc(int sfactor, int dfactor) {

            }

            @Override
            public void glClear(int mask) {

            }

            @Override
            public void glClearColor(float red, float green, float blue, float alpha) {

            }

            @Override
            public void glClearDepthf(float depth) {

            }

            @Override
            public void glClearStencil(int s) {

            }

            @Override
            public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {

            }

            @Override
            public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {

            }

            @Override
            public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {

            }

            @Override
            public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {

            }

            @Override
            public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {

            }

            @Override
            public void glCullFace(int mode) {

            }

            @Override
            public void glDeleteTextures(int n, IntBuffer textures) {

            }

            @Override
            public void glDeleteTexture(int texture) {

            }

            @Override
            public void glDepthFunc(int func) {

            }

            @Override
            public void glDepthMask(boolean flag) {

            }

            @Override
            public void glDepthRangef(float zNear, float zFar) {

            }

            @Override
            public void glDisable(int cap) {

            }

            @Override
            public void glDrawArrays(int mode, int first, int count) {

            }

            @Override
            public void glDrawElements(int mode, int count, int type, Buffer indices) {

            }

            @Override
            public void glEnable(int cap) {

            }

            @Override
            public void glFinish() {

            }

            @Override
            public void glFlush() {

            }

            @Override
            public void glFrontFace(int mode) {

            }

            @Override
            public void glGenTextures(int n, IntBuffer textures) {

            }

            @Override
            public int glGenTexture() {
                return 0;
            }

            @Override
            public int glGetError() {
                return 0;
            }

            @Override
            public void glGetIntegerv(int pname, IntBuffer params) {

            }

            @Override
            public String glGetString(int name) {
                return null;
            }

            @Override
            public void glHint(int target, int mode) {

            }

            @Override
            public void glLineWidth(float width) {

            }

            @Override
            public void glPixelStorei(int pname, int param) {

            }

            @Override
            public void glPolygonOffset(float factor, float units) {

            }

            @Override
            public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {

            }

            @Override
            public void glScissor(int x, int y, int width, int height) {

            }

            @Override
            public void glStencilFunc(int func, int ref, int mask) {

            }

            @Override
            public void glStencilMask(int mask) {

            }

            @Override
            public void glStencilOp(int fail, int zfail, int zpass) {

            }

            @Override
            public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {

            }

            @Override
            public void glTexParameterf(int target, int pname, float param) {

            }

            @Override
            public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {

            }

            @Override
            public void glViewport(int x, int y, int width, int height) {

            }

            @Override
            public void glAttachShader(int program, int shader) {

            }

            @Override
            public void glBindAttribLocation(int program, int index, String name) {

            }

            @Override
            public void glBindBuffer(int target, int buffer) {

            }

            @Override
            public void glBindFramebuffer(int target, int framebuffer) {

            }

            @Override
            public void glBindRenderbuffer(int target, int renderbuffer) {

            }

            @Override
            public void glBlendColor(float red, float green, float blue, float alpha) {

            }

            @Override
            public void glBlendEquation(int mode) {

            }

            @Override
            public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {

            }

            @Override
            public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {

            }

            @Override
            public void glBufferData(int target, int size, Buffer data, int usage) {

            }

            @Override
            public void glBufferSubData(int target, int offset, int size, Buffer data) {

            }

            @Override
            public int glCheckFramebufferStatus(int target) {
                return 0;
            }

            @Override
            public void glCompileShader(int shader) {

            }

            @Override
            public int glCreateProgram() {
                return 0;
            }

            @Override
            public int glCreateShader(int type) {
                return 0;
            }

            @Override
            public void glDeleteBuffer(int buffer) {

            }

            @Override
            public void glDeleteBuffers(int n, IntBuffer buffers) {

            }

            @Override
            public void glDeleteFramebuffer(int framebuffer) {

            }

            @Override
            public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {

            }

            @Override
            public void glDeleteProgram(int program) {

            }

            @Override
            public void glDeleteRenderbuffer(int renderbuffer) {

            }

            @Override
            public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {

            }

            @Override
            public void glDeleteShader(int shader) {

            }

            @Override
            public void glDetachShader(int program, int shader) {

            }

            @Override
            public void glDisableVertexAttribArray(int index) {

            }

            @Override
            public void glDrawElements(int mode, int count, int type, int indices) {

            }

            @Override
            public void glEnableVertexAttribArray(int index) {

            }

            @Override
            public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {

            }

            @Override
            public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {

            }

            @Override
            public int glGenBuffer() {
                return 0;
            }

            @Override
            public void glGenBuffers(int n, IntBuffer buffers) {

            }

            @Override
            public void glGenerateMipmap(int target) {

            }

            @Override
            public int glGenFramebuffer() {
                return 0;
            }

            @Override
            public void glGenFramebuffers(int n, IntBuffer framebuffers) {

            }

            @Override
            public int glGenRenderbuffer() {
                return 0;
            }

            @Override
            public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {

            }

            @Override
            public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
                return null;
            }

            @Override
            public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
                return null;
            }

            @Override
            public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders) {

            }

            @Override
            public int glGetAttribLocation(int program, String name) {
                return 0;
            }

            @Override
            public void glGetBooleanv(int pname, Buffer params) {

            }

            @Override
            public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {

            }

            @Override
            public void glGetFloatv(int pname, FloatBuffer params) {

            }

            @Override
            public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {

            }

            @Override
            public void glGetProgramiv(int program, int pname, IntBuffer params) {

            }

            @Override
            public String glGetProgramInfoLog(int program) {
                return null;
            }

            @Override
            public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {

            }

            @Override
            public void glGetShaderiv(int shader, int pname, IntBuffer params) {

            }

            @Override
            public String glGetShaderInfoLog(int shader) {
                return null;
            }

            @Override
            public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {

            }

            @Override
            public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {

            }

            @Override
            public void glGetTexParameteriv(int target, int pname, IntBuffer params) {

            }

            @Override
            public void glGetUniformfv(int program, int location, FloatBuffer params) {

            }

            @Override
            public void glGetUniformiv(int program, int location, IntBuffer params) {

            }

            @Override
            public int glGetUniformLocation(int program, String name) {
                return 0;
            }

            @Override
            public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {

            }

            @Override
            public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {

            }

            @Override
            public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer) {

            }

            @Override
            public boolean glIsBuffer(int buffer) {
                return false;
            }

            @Override
            public boolean glIsEnabled(int cap) {
                return false;
            }

            @Override
            public boolean glIsFramebuffer(int framebuffer) {
                return false;
            }

            @Override
            public boolean glIsProgram(int program) {
                return false;
            }

            @Override
            public boolean glIsRenderbuffer(int renderbuffer) {
                return false;
            }

            @Override
            public boolean glIsShader(int shader) {
                return false;
            }

            @Override
            public boolean glIsTexture(int texture) {
                return false;
            }

            @Override
            public void glLinkProgram(int program) {

            }

            @Override
            public void glReleaseShaderCompiler() {

            }

            @Override
            public void glRenderbufferStorage(int target, int internalformat, int width, int height) {

            }

            @Override
            public void glSampleCoverage(float value, boolean invert) {

            }

            @Override
            public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {

            }

            @Override
            public void glShaderSource(int shader, String string) {

            }

            @Override
            public void glStencilFuncSeparate(int face, int func, int ref, int mask) {

            }

            @Override
            public void glStencilMaskSeparate(int face, int mask) {

            }

            @Override
            public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {

            }

            @Override
            public void glTexParameterfv(int target, int pname, FloatBuffer params) {

            }

            @Override
            public void glTexParameteri(int target, int pname, int param) {

            }

            @Override
            public void glTexParameteriv(int target, int pname, IntBuffer params) {

            }

            @Override
            public void glUniform1f(int location, float x) {

            }

            @Override
            public void glUniform1fv(int location, int count, FloatBuffer v) {

            }

            @Override
            public void glUniform1fv(int location, int count, float[] v, int offset) {

            }

            @Override
            public void glUniform1i(int location, int x) {

            }

            @Override
            public void glUniform1iv(int location, int count, IntBuffer v) {

            }

            @Override
            public void glUniform1iv(int location, int count, int[] v, int offset) {

            }

            @Override
            public void glUniform2f(int location, float x, float y) {

            }

            @Override
            public void glUniform2fv(int location, int count, FloatBuffer v) {

            }

            @Override
            public void glUniform2fv(int location, int count, float[] v, int offset) {

            }

            @Override
            public void glUniform2i(int location, int x, int y) {

            }

            @Override
            public void glUniform2iv(int location, int count, IntBuffer v) {

            }

            @Override
            public void glUniform2iv(int location, int count, int[] v, int offset) {

            }

            @Override
            public void glUniform3f(int location, float x, float y, float z) {

            }

            @Override
            public void glUniform3fv(int location, int count, FloatBuffer v) {

            }

            @Override
            public void glUniform3fv(int location, int count, float[] v, int offset) {

            }

            @Override
            public void glUniform3i(int location, int x, int y, int z) {

            }

            @Override
            public void glUniform3iv(int location, int count, IntBuffer v) {

            }

            @Override
            public void glUniform3iv(int location, int count, int[] v, int offset) {

            }

            @Override
            public void glUniform4f(int location, float x, float y, float z, float w) {

            }

            @Override
            public void glUniform4fv(int location, int count, FloatBuffer v) {

            }

            @Override
            public void glUniform4fv(int location, int count, float[] v, int offset) {

            }

            @Override
            public void glUniform4i(int location, int x, int y, int z, int w) {

            }

            @Override
            public void glUniform4iv(int location, int count, IntBuffer v) {

            }

            @Override
            public void glUniform4iv(int location, int count, int[] v, int offset) {

            }

            @Override
            public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {

            }

            @Override
            public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {

            }

            @Override
            public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {

            }

            @Override
            public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {

            }

            @Override
            public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {

            }

            @Override
            public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {

            }

            @Override
            public void glUseProgram(int program) {

            }

            @Override
            public void glValidateProgram(int program) {

            }

            @Override
            public void glVertexAttrib1f(int indx, float x) {

            }

            @Override
            public void glVertexAttrib1fv(int indx, FloatBuffer values) {

            }

            @Override
            public void glVertexAttrib2f(int indx, float x, float y) {

            }

            @Override
            public void glVertexAttrib2fv(int indx, FloatBuffer values) {

            }

            @Override
            public void glVertexAttrib3f(int indx, float x, float y, float z) {

            }

            @Override
            public void glVertexAttrib3fv(int indx, FloatBuffer values) {

            }

            @Override
            public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {

            }

            @Override
            public void glVertexAttrib4fv(int indx, FloatBuffer values) {

            }

            @Override
            public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr) {

            }

            @Override
            public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {

            }
        };
        Gdx.gl = Gdx.gl20;

        //run postRunnable at once
        Gdx.app = mock(Application.class);
        doAnswer(invocation -> {
            invocation.getArgumentAt(0, Runnable.class).run();
            return null;
        }).when(Gdx.app).postRunnable(any(Runnable.class));


        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(1);
        when(Gdx.graphics.getHeight()).thenReturn(1);
    }

    // After we are done, clean up the application
    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }
}