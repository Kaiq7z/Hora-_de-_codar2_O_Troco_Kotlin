import kotlin.system.exitProcess
import java.util.Locale

var saldo = 100.50
var nome = ""
val extrato = mutableListOf(
    "Depósito inicial: R$ 200,00",
    "Compra no mercado: R$ 59,50",
    "Compra na farmácia: R$ 40,00"
)

fun main() {
    print("Qual é o seu nome? ")
    nome = lerEntrada()
    println("Olá $nome é um prazer ter você por aqui!")
    inicio()
}

fun lerEntrada(): String {
    val entrada = readLine()
    if (entrada == null) {
        exitProcess(0)
    }
    return entrada.trim()
}

fun dinheiro(valor: Double): String {
    return "R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%.2f", valor)
}

fun inicio() {
    while (true) {
        println("\nEscolha uma opção:")
        println("1 - Saldo")
        println("2 - Extrato")
        println("3 - Saque")
        println("4 - Depósito")
        println("5 - Transferência")
        println("6 - Sair")
        val escolha = lerEntrada().toIntOrNull()
        when (escolha) {
            1 -> verSaldo()
            2 -> verExtrato()
            3 -> fazerSaque()
            4 -> fazerDeposito()
            5 -> fazerTransferencia()
            6 -> sair()
            else -> erro()
        }
    }
}

fun verificarSenha(): Boolean {
    print("Informe a senha: ")
    val senha = lerEntrada()
    if (senha == "3589") {
        return true
    } else {
        println("Senha incorreta.")
        return false
    }
}

// tailrec evita acumular chamadas quando a senha está errada.
tailrec fun verSaldo() {
    if (!verificarSenha()) {
        return verSaldo()
    }
    println("Seu saldo atual é: ${dinheiro(saldo)}")
}

tailrec fun verExtrato() {
    if (!verificarSenha()) {
        return verExtrato()
    }
    println("\nExtrato:")
    for (movimentacao in extrato) {
        println(movimentacao)
    }
}

fun fazerDeposito() {
    print("Qual o valor para depósito? ")
    val deposito = lerEntrada().replace(',', '.').toDoubleOrNull()
    if (deposito == null || !deposito.isFinite() || deposito <= 0) {
        println("Operação não autorizada")
        return
    }
    if (!(saldo + deposito).isFinite()) {
        println("Operação não autorizada")
        return
    }
    saldo += deposito
    extrato.add("Depósito: ${dinheiro(deposito)}")
    println("Depósito realizado com sucesso.")
}

tailrec fun fazerSaque() {
    if (!verificarSenha()) {
        return fazerSaque()
    }
    print("Qual o valor para saque? ")
    val saque = lerEntrada().replace(',', '.').toDoubleOrNull()
    if (saque == null || !saque.isFinite() || saque <= 0 || saque > saldo) {
        println("Operação não autorizada")
        return
    }
    saldo -= saque
    extrato.add("Saque: ${dinheiro(saque)}")
    println("Saque realizado com sucesso.")
}

tailrec fun fazerTransferencia() {
    if (!verificarSenha()) {
        return fazerTransferencia()
    }
    var conta: String
    while (true) {
        print("Informe o número da conta: ")
        conta = readLine() ?: exitProcess(0)
        var contaValida = conta.isNotEmpty()
        for (caractere in conta) {
            if (caractere < '0' || caractere > '9') {
                contaValida = false
            }
        }
        if (contaValida) {
            break
        }
        println("Por favor, informe uma conta somente com números.")
    }
    print("Qual o valor da transferência? ")
    val transferencia = lerEntrada().replace(',', '.').toDoubleOrNull()
    if (transferencia == null || !transferencia.isFinite() || transferencia <= 0 || transferencia > saldo) {
        println("Operação não autorizada")
        return
    }
    saldo -= transferencia
    extrato.add("Transferência para a conta $conta: ${dinheiro(transferencia)}")
    println("Transferência realizada com sucesso.")
}

fun erro() {
    println("Por favor, informe um número entre 1 a 6.")
}

fun sair() {
    while (true) {
        print("Você deseja sair? (S/N): ")
        val confirma = lerEntrada().uppercase()
        when (confirma) {
            "S" -> {
                println("$nome, foi um prazer ter você por aqui!")
                exitProcess(0)
            }
            "N" -> return
            else -> println("Informe S ou N.")
        }
    }
}
