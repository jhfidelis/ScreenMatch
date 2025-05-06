package br.com.alura.screenmatch.exceptions;

public class ErroDeConversaoException extends RuntimeException {
    private String mensagem;

    public ErroDeConversaoException(String msg) {
        this.mensagem = msg;
    }

    public String getMensagem() {
        return this.mensagem;
    }
}
