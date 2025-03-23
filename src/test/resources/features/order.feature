Feature: Gerenciamento de Pedidos

  Scenario: Buscar um pedido existente pelo ID
    Given um pedido com ID "123e4567-e89b-12d3-a456-426614174000" existe no sistema
    When faço uma requisição GET para "/api/order/123e4567-e89b-12d3-a456-426614174000"
    Then a resposta deve ter status 200
    And deve conter os detalhes do pedido

  Scenario: Criar um novo pedido
    Given um novo pedido válido
    When faço uma requisição POST para "/api/order"
    Then a resposta deve ter status 200
    And deve conter um ID de pedido válido

  Scenario: Atualizar o status de um pedido existente
    Given um pedido com ID "123e4567-e89b-12d3-a456-426614174000" existe no sistema
    And o novo status é "2"
    When faço uma requisição PATCH para "/api/order/123e4567-e89b-12d3-a456-426614174000/status"
    Then a resposta deve ter status 200