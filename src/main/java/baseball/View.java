package baseball;

import camp.nextstep.edu.missionutils.Console;

public class View {

    public String input() {
        System.out.print("숫자를 입력해주세요 :");
        return Console.readLine();
    }
}