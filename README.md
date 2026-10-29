# Microsserviço de Notificação

Microsserviço de notificações por e-mail do **Portal de Cursos**.

## Sobre

Este serviço tem uma única responsabilidade: **disparar e-mails** quando o status de uma submissão de projeto muda. Ele **não possui banco de dados** e **não decide quando enviar**: quem decide é o microsserviço de Projetos, que chama este serviço informando o status, o projeto e os destinatários.

## Fluxo de notificações

| Status do projeto     | Significado                                   | Quem recebe o e-mail |
|-----------------------|-----------------------------------------------|----------------------|
| `EM_ANALISE`          | Projeto submetido, aguardando o professor     | Professor            |
| `APROVADO`            | Professor aprovou a submissão                 | Aluno                |
| `RECUSADO`            | Professor recusou a submissão                 | Aluno                |
| `ANALISE_PUBLICACAO`  | Aguardando análise do coordenador             | Coordenador do curso |
| `PUBLICADO`           | Coordenador aceitou a publicação              | Professor e aluno    |
| `RECUSADO_PUBLICACAO` | Coordenador recusou a publicação              | Professor e aluno    |
| `REVISAO`             | Projeto em revisão                            | Aluno                |

## Tecnologias

- Java + Spring Boot
- Spring Web
- Spring Mail (SMTP do Gmail com senha de app)
- Thymeleaf (templates HTML dos e-mails)
- Bean Validation
- Lombok

## Configuração

O envio usa uma conta Gmail com **senha de app**. As credenciais são lidas de variáveis de ambiente (nunca coloque senhas no repositório):

| Variável        | Descrição                         |
|-----------------|-----------------------------------|
| `MAIL_USERNAME` | E-mail remetente (Gmail)          |
| `MAIL_PASSWORD` | Senha de app gerada no Google     |

## Testando pelo Postman

Como o serviço é independente, é possível testá-lo isoladamente enviando uma requisição `POST` com os dados da notificação (status, nome do projeto, destinatários etc.). Os detalhes do endpoint serão documentados aqui conforme o desenvolvimento avançar.

## Integração

Futuramente, o serviço também consumirá eventos de uma fila (RabbitMQ), reaproveitando a mesma lógica de envio.

## Equipe

Carlos Eduardo, Jadson Teixeira, João Pedro e Luana Gomes.